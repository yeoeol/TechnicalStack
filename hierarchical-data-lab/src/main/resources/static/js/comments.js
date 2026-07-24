document.addEventListener("DOMContentLoaded", () => {
  document.querySelectorAll(".tree-comment-list").forEach(initializeCommentTree);
});

function initializeCommentTree(tree) {
  const nodes = Array.from(tree.querySelectorAll(".comment-node"));

  nodes.forEach((node) => {
    if (getDepth(node) > 0) {
      node.hidden = true;
    }
  });

  tree.querySelectorAll(".reply-toggle").forEach((button) => {
    const owner = button.closest(".comment-node");

    setToggleState(button, false);
    button.addEventListener("click", () => toggleReplies(owner, button));
  });
}

function toggleReplies(owner, button) {
  const isExpanded = button.getAttribute("aria-expanded") === "true";

  if (isExpanded) {
    collapseReplies(owner);
    return;
  }

  const maximumDepth = getDepth(owner) + 2;
  getDescendants(owner).forEach((node) => {
    node.hidden = getDepth(node) > maximumDepth;
  });
  setToggleState(button, true);
}

function collapseReplies(owner) {
  getDescendants(owner).forEach((node) => {
    node.hidden = true;
    node.querySelectorAll(":scope > .comment-card .reply-toggle").forEach((button) => {
      setToggleState(button, false);
    });
  });

  const ownerButton = owner.querySelector(":scope > .comment-card .reply-toggle");
  if (ownerButton) {
    setToggleState(ownerButton, false);
  }
}

function getDescendants(owner) {
  return Array.from(owner.querySelectorAll(".comment-node"));
}

function getDepth(node) {
  return Number(node.dataset.depth);
}

function setToggleState(button, expanded) {
  const count = button.dataset.replyCount;
  const text = button.querySelector(".reply-toggle-text");

  button.setAttribute("aria-expanded", String(expanded));
  text.textContent = expanded ? "답글 숨기기" : `답글 ${count}개`;
}
