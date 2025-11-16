package com.example.redislock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    @Transactional
    public void ticketing(Long ticketId, Long quantity) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow();
        ticket.decrease(quantity);
        ticketRepository.saveAndFlush(ticket);
    }

    @Transactional
    public void pessimisticTicketing(Long ticketId, Long quantity) {
        Ticket ticket = ticketRepository.findByIdForUpdate(ticketId).orElseThrow();
        ticket.decrease(quantity);
        ticketRepository.saveAndFlush(ticket);
    }

    @Transactional
    @RedissonLock(value = "#ticketId")
    public void redissonTicketing(Long ticketId, Long quantity) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow();
        ticket.decrease(quantity);
        ticketRepository.saveAndFlush(ticket);
    }
}
