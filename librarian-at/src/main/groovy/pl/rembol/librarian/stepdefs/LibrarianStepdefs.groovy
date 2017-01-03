package pl.rembol.librarian.stepdefs

import cucumber.api.groovy.EN
import cucumber.api.groovy.Hooks
import pl.rembol.librarian.BookActions
import pl.rembol.librarian.BorrowActions
import pl.rembol.librarian.ClientsActions

this.metaClass.mixin(Hooks)
this.metaClass.mixin(EN)

def clientActions = new ClientsActions()
def borrowActions = new BorrowActions()
def bookActions = new BookActions()

When(~"client is registered with name (.*)") { String name ->
    clientActions.createClient(name)
}

Given(~"a new client called (.*)") { String name ->
    clientActions.createClient(name)
}

Then(~"newly registered client has name (.*)") { String name ->
    assert clientActions.findClient(name) == name
}

Given(~"an empty library") { ->
    borrowActions.clearAll()
    clientActions.clearAll()
    bookActions.clearAll()
}

When(~"a new book (.*) by (.*) is added") { String name, String author ->
    bookActions.add(name, author)
}

Then(~"book (.*) can be found in library") { String name ->
    assert bookActions.canBeFound(name)
}

Then(~"book (.*) (can|can't) be borrowed") { String name, String can ->
    assert bookActions.canBeBorrowed(name) == (can == 'can')
}

When(~"(.*) borrows book (.*)") { String client, String book ->
    borrowActions.borrow(client, book)
}

When(~"we search books by author (.*)") { String author ->
    bookActions.searchByAuthor(author)
}

Then(~"book (.*) is found amongst them") { String book ->
    assert bookActions.isFound(book)
}

Then(~"book (.*) (is|is not) amongst books borrowed by (.*)") { String is, String book, String client ->
    assert borrowActions.isAmongstBorrowedBy(client, book) == (is == 'is')
}

Then(~"(.*) can borrow (\\d+) books") { String name, int canBorrow ->
    assert clientActions.clientLimit(name) == canBorrow
}

When(~"(.*) returns book (.*)") { String client, String book ->
    borrowActions.return(client, book)
}

Then(~"operation is refused with message '(.*)'") { String message ->
    borrowActions.operationRefused()
    assert borrowActions.operationMessage() == message
}