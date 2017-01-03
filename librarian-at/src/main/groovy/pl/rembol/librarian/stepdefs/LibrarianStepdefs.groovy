package pl.rembol.librarian.stepdefs

import cucumber.api.groovy.EN
import cucumber.api.groovy.Hooks
import pl.rembol.librarian.ClientsActions

this.metaClass.mixin(Hooks)
this.metaClass.mixin(EN)

def clientActions = new ClientsActions()

When(~"client is registered with name (.*)") { String name ->
    clientActions.createClient(name)
}

Then(~"newly registered client has name (.*)") { String name ->
    assert clientActions.findClient(name) == name
}