Feature: Books

Background:
  Given an empty library

Scenario: Adding a book
  When a new book Dune by Frank Herbert is added
  Then book Dune can be found in library
  And book Dune can be borrowed

Scenario: Adding a book
  Given a new client called RemboL
  And a new book Dune by Frank Herbert is added
  When RemboL borrows book Dune
  Then book Dune can't be borrowed


Scenario: Searching book by author
  Given a new book Dune by Frank Herbert is added
  When we search books by author Frank Herbert
  Then book Dune is found amongst them

