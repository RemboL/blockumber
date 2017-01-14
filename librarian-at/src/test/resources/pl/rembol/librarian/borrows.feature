Feature: Borrows

Background:
  Given an empty library

Scenario: Borrowing a book
  Given a new book Dune by Frank Herbert is added
  And a new client called RemboL
  When RemboL borrows book Dune
  Then book Dune is amongst books borrowed by RemboL
  And RemboL can borrow 2 books

Scenario Outline: Borrowing a book
  Given a new book Dune by Frank Herbert is added
  And a new book Children of Dune by Frank Herbert is added
  And a new client called RemboL
  And a new client called Marcin
  And Marcin borrows book Children of Dune
  When RemboL borrows book <book>
  Then book <book> <is borrowed?> amongst books borrowed by RemboL
  And RemboL can borrow <limit> books

  Examples:
    | book             | is borrowed? | limit |
    | Dune             | is           | 2     |
    | Children of Dune | is not       | 3     |

Scenario: Borrowing doubled book
  Given a new book Dune by Frank Herbert is added
  And a new book Dune by Frank Herbert is added
  And a new client called RemboL
  And a new client called Marcin
  And RemboL borrows book Dune
  When Marcin borrows book Dune
  Then book Dune is amongst books borrowed by Marcin
  And Marcin can borrow 2 books

Scenario: Trying to borrow same book twice
  Given a new book Dune by Frank Herbert is added
  And a new book Dune by Frank Herbert is added
  And a new client called RemboL
  And RemboL borrows book Dune
  When RemboL borrows book Dune
  Then operation is refused with message 'client "RemboL" already borrowed book "Dune"'
  And RemboL can borrow 2 books

Scenario: Trying to borrow four books
  Given a new book Dune by Frank Herbert is added
  And a new book Dune Messiah by Frank Herbert is added
  And a new book Children of Dune by Frank Herbert is added
  And a new book God Emperor of Dune by Frank Herbert is added
  And a new client called RemboL
  And RemboL borrows book Dune
  And RemboL borrows book Dune Messiah
  And RemboL borrows book Children of Dune
  When RemboL borrows book God Emperor of Dune
  Then operation is refused with message 'client "RemboL" already borrowed three books'
  And RemboL can borrow 0 books

Scenario: Returning a book
  Given a new book Dune by Frank Herbert is added
  And a new client called RemboL
  And RemboL borrows book Dune
  When RemboL returns book Dune
  Then book Dune is not amongst books borrowed by RemboL
  And RemboL can borrow 3 books

Scenario: Trying to return not borrowed
  Given a new book Dune by Frank Herbert is added
  And a new client called RemboL
  When RemboL returns book Dune
  Then operation is refused with message 'client "RemboL" doesn't have book "Dune"'
  And RemboL can borrow 3 books