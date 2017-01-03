Feature: Clients

Background:
  Given an empty library

Scenario: Creating a client
    When client is registered with name RemboL
    Then newly registered client has name RemboL
    And RemboL can borrow 3 books