(() => {
  "use strict";

  const mockRecords = [
    {
      id: 101,
      parentId: null,
      depth: 0,
      author: "민준",
      content: "재귀 CTE의 기준 행(anchor)에는 루트 댓글만 선택하면 될까요?",
      createdAt: "12분 전"
    },
    {
      id: 102,
      parentId: 101,
      depth: 1,
      author: "서연",
      content: "네. parent_id가 NULL인 행을 기준으로 시작한 뒤 자식 행을 재귀적으로 조인하면 됩니다.",
      createdAt: "9분 전"
    },
    {
      id: 103,
      parentId: 102,
      depth: 2,
      author: "민준",
      content: "그럼 depth도 CTE 안에서 1씩 증가시키면 되겠네요.",
      createdAt: "7분 전"
    },
    {
      id: 104,
      parentId: 103,
      depth: 3,
      author: "지우",
      content: "맞습니다. 정렬용 경로(path)도 함께 만들면 트리 순서를 유지하기 편해요.",
      createdAt: "5분 전"
    },
    {
      id: 105,
      parentId: 101,
      depth: 1,
      author: "도윤",
      content: "순환 참조를 막기 위한 조건도 함께 고려해보면 좋은 실습이 될 것 같아요.",
      createdAt: "4분 전"
    },
    {
      id: 201,
      parentId: null,
      depth: 0,
      author: "하은",
      content: "직계 자식 조회와 전체 트리 조회의 쿼리를 따로 비교해보려고 합니다.",
      createdAt: "18분 전"
    },
    {
      id: 202,
      parentId: 201,
      depth: 1,
      author: "시우",
      content: "직계 자식 조회는 일반 조건 조회로도 충분해서 실행 계획 차이를 보기 좋겠네요.",
      createdAt: "14분 전"
    },
    {
      id: 301,
      parentId: null,
      depth: 0,
      author: "수아",
      content: "대댓글이 아주 깊어졌을 때 화면에서는 들여쓰기 폭을 어떻게 처리하나요?",
      createdAt: "25분 전"
    }
  ];

  function withChildCount(records) {
    const counts = records.reduce((result, record) => {
      if (record.parentId !== null) {
        result.set(record.parentId, (result.get(record.parentId) || 0) + 1);
      }
      return result;
    }, new Map());

    return records.map((record) => ({
      ...record,
      childCount: record.childCount ?? counts.get(record.id) ?? 0
    }));
  }

  const sourceRecords = withChildCount(mockRecords);

  // 실제 API가 준비되면 이 객체의 세 함수만 fetch 호출로 교체합니다.
  const commentService = {
    async getRootComments() {
      const serverRecords = window.__ROOT_COMMENTS__;
      if (Array.isArray(serverRecords)) {
        return withChildCount(serverRecords);
      }
      return sourceRecords.filter((comment) => comment.parentId === null);
    },

    async getChildComments(parentId) {
      return sourceRecords.filter((comment) => comment.parentId === parentId);
    },

    async getCommentTree() {
      return sourceRecords;
    }
  };

  const state = {
    records: new Map(),
    expandedIds: new Set(),
    activeReplyId: null,
    view: "roots",
    nextId: 1000
  };

  const elements = {
    list: document.querySelector("#comment-list"),
    count: document.querySelector("#comment-count"),
    status: document.querySelector("#load-status"),
    rootForm: document.querySelector("#root-comment-form"),
    replyTemplate: document.querySelector("#reply-form-template"),
    viewButtons: document.querySelectorAll("[data-view]")
  };

  function escapeHtml(value) {
    const element = document.createElement("div");
    element.textContent = String(value);
    return element.innerHTML;
  }

  function upsertRecords(records) {
    records.forEach((record) => state.records.set(Number(record.id), {
      ...record,
      id: Number(record.id),
      parentId: record.parentId === null ? null : Number(record.parentId),
      depth: Number(record.depth || 0),
      childCount: Number(record.childCount || 0)
    }));
  }

  function childrenOf(parentId) {
    return [...state.records.values()]
      .filter((record) => record.parentId === parentId)
      .sort((a, b) => a.id - b.id);
  }

  function renderComment(comment) {
    const isExpanded = state.expandedIds.has(comment.id);
    const loadedChildren = childrenOf(comment.id);
    const hasChildren = comment.childCount > 0 || loadedChildren.length > 0;
    const childLabel = isExpanded
      ? "답글 접기"
      : `답글 ${comment.childCount || loadedChildren.length}개 보기`;

    const childrenMarkup = isExpanded && loadedChildren.length > 0
      ? `<div class="child-comments">${loadedChildren.map(renderComment).join("")}</div>`
      : "";

    return `
      <article class="comment-node" data-comment-id="${comment.id}" data-depth="${comment.depth}">
        <div class="comment-card">
          <div class="avatar" aria-hidden="true">${escapeHtml(comment.author).slice(0, 1)}</div>
          <div>
            <div class="comment-meta">
              <span class="comment-author">${escapeHtml(comment.author)}</span>
              <span class="comment-time">${escapeHtml(comment.createdAt)}</span>
              <span class="depth-badge">depth ${comment.depth}</span>
            </div>
            <p class="comment-content">${escapeHtml(comment.content)}</p>
            <div class="comment-actions">
              <button type="button" class="text-button" data-action="open-reply">답글 달기</button>
              ${hasChildren ? `
                <button type="button" class="text-button child-toggle" data-action="toggle-children"
                        aria-expanded="${isExpanded}">${childLabel}</button>
              ` : ""}
            </div>
          </div>
        </div>
        ${state.activeReplyId === comment.id ? renderReplyForm(comment.id) : ""}
        ${childrenMarkup}
      </article>
    `;
  }

  function renderReplyForm(commentId) {
    const fragment = elements.replyTemplate.content.cloneNode(true);
    const wrapper = document.createElement("div");
    const form = fragment.querySelector("form");
    const label = fragment.querySelector("label");
    const textarea = fragment.querySelector("textarea");
    form.dataset.parentId = String(commentId);
    textarea.id = `reply-content-${commentId}`;
    label.htmlFor = textarea.id;
    wrapper.appendChild(fragment);
    return wrapper.innerHTML;
  }

  function render() {
    const roots = childrenOf(null);
    elements.count.textContent = String(state.records.size);
    elements.list.innerHTML = roots.length > 0
      ? roots.map(renderComment).join("")
      : `<div class="empty-state">아직 댓글이 없습니다.<br>첫 번째 댓글을 남겨보세요.</div>`;

    if (state.activeReplyId !== null) {
      elements.list.querySelector(`#reply-content-${state.activeReplyId}`)?.focus();
    }
  }

  function setStatus(message) {
    elements.status.textContent = message;
  }

  function setActiveView(view) {
    state.view = view;
    elements.viewButtons.forEach((button) => {
      button.classList.toggle("is-active", button.dataset.view === view);
    });
  }

  async function loadRoots() {
    setStatus("루트 댓글을 불러오는 중입니다.");
    const roots = await commentService.getRootComments();
    state.records.clear();
    state.expandedIds.clear();
    state.activeReplyId = null;
    upsertRecords(roots);
    setActiveView("roots");
    setStatus("루트 댓글만 조회했습니다.");
    render();
  }

  async function loadFullTree() {
    setStatus("전체 댓글 트리를 불러오는 중입니다.");
    const records = await commentService.getCommentTree();
    state.records.clear();
    state.expandedIds.clear();
    upsertRecords(records);
    records.forEach((record) => {
      if (record.childCount > 0 || records.some((child) => child.parentId === record.id)) {
        state.expandedIds.add(Number(record.id));
      }
    });
    setActiveView("tree");
    setStatus("전체 트리를 조회해 모든 깊이를 펼쳤습니다.");
    render();
  }

  async function toggleChildren(commentId) {
    if (state.expandedIds.has(commentId)) {
      state.expandedIds.delete(commentId);
      render();
      return;
    }

    setStatus(`#${commentId} 댓글의 직계 답글을 불러오는 중입니다.`);
    const children = await commentService.getChildComments(commentId);
    upsertRecords(children);
    state.expandedIds.add(commentId);
    setStatus(`직계 답글 ${children.length}개를 조회했습니다.`);
    render();
  }

  function addComment(parentId, content) {
    const parent = parentId === null ? null : state.records.get(parentId);
    const id = state.nextId++;
    state.records.set(id, {
      id,
      parentId,
      depth: parent ? parent.depth + 1 : 0,
      author: "나",
      content,
      createdAt: "방금 전",
      childCount: 0
    });

    if (parent) {
      parent.childCount += 1;
      state.expandedIds.add(parent.id);
    }
    state.activeReplyId = null;
    setStatus(parent ? "답글을 화면에 임시로 추가했습니다." : "새 댓글을 화면에 임시로 추가했습니다.");
    render();
  }

  elements.rootForm.addEventListener("submit", (event) => {
    event.preventDefault();
    const textarea = event.currentTarget.elements.content;
    const content = textarea.value.trim();
    if (!content) return;
    addComment(null, content);
    event.currentTarget.reset();
  });

  elements.list.addEventListener("click", async (event) => {
    const button = event.target.closest("button[data-action]");
    if (!button) return;
    const node = button.closest("[data-comment-id]");
    const commentId = Number(node?.dataset.commentId);

    if (button.dataset.action === "open-reply") {
      state.activeReplyId = state.activeReplyId === commentId ? null : commentId;
      render();
    }

    if (button.dataset.action === "cancel-reply") {
      state.activeReplyId = null;
      render();
    }

    if (button.dataset.action === "toggle-children") {
      await toggleChildren(commentId);
    }
  });

  elements.list.addEventListener("submit", (event) => {
    const form = event.target.closest(".reply-form");
    if (!form) return;
    event.preventDefault();
    const parentId = Number(form.dataset.parentId);
    const content = form.elements.content.value.trim();
    if (!content) return;
    addComment(parentId, content);
  });

  elements.viewButtons.forEach((button) => {
    button.addEventListener("click", () => {
      if (button.dataset.view === "tree") {
        loadFullTree();
      } else {
        loadRoots();
      }
    });
  });

  loadRoots().catch(() => {
    setStatus("댓글을 불러오지 못했습니다.");
  });
})();
