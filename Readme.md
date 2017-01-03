# Blockumber

## What is?

Blockumber is an open source project that allows you to create cucumber scenarios not with text editor, but with [blockly] - hence the name, Blockly + Cucumber = Blockumber.

![Alt text](/blockumber.png?raw=true "Blockumber in action")

## Reasoning behind project

Most of developers, when they are first introduced to [behavior-driven development] are told that it's a gorgeous tool for bridging business requirements and autmated tests - developers provide a set of step definitions (or glue) and business can create their own scenarios with provided glue.

Then most of developers after few months or years of practice with BDD will tell you that it in most cases won't work like that.

Usual reason is that business is usually either not elastic enough to understand the principles of step definitions or is annoyed by limitations of those definitions that are often hidden under provided scenario editor (though I had the pleasure of working with Product Owner that lied in the sweet spot between - but that might be due to the fact of her IT education).

I'll give you an example.

Product owner provides you with a scenario that they want to test:
```given a basket with 10 cucumbers
```when I take 4 cucumbers
```then I have 6 cucumbers left in the basket

The you develop your stepdefs accordingly:
```groovy
Given(~'a basket with (\\d+) cucumbers') { ... }
When(~'I take (\\d+) cucumbers') { ... }
Then(~'I have (\\d+) cucumbers left in the basket') { ... }
```
All is peachy, devs are happy because they automated the oh-so-important cucumber/basket logic, PO's happy because he can now in his [editor] create scenarios that will run automatically.

But the PO starts to think about other scenarios:
```
given a basket with 10 cucumbers
when I take 10 cucumbers
then I have an empty basket
```

```
given a basket with 10 cucumbers and a tomato
when I take 4 cucumbers
then I have veggie salad left in the basket
```

Now two things can happen:
* either product owner notices that tests are red because he is not hitting exact step expressions and he gets angry at limitations of the tools provided
* or product owner notices that tests are red and he gets angry because oh-so-important functionality doesn't work as it should

Either way, this usually means that devs have more work with debugging, coding additional stepdefs, etc., and (hopefully) at some point business notices that this work setup where they're trying to write scenarios is not cost effective.

But this course of action is the result of simple fact - PO can do in the editor much more than the stepdefs allow.

That's basically the reason behind - to provide the editor that allows no more and no less than stepdefs allow.

## How it works?

The repo is divided into three modules
* librarian - an example spring-boot application that will be tested
* librarian-at - separate module containing cucumber scenarios (Why? Because in my experience in big projects' ATs like to migrate to separate module and be run against living application)
* blockumber-main - the meat behind it all

To set up, you need follow the librarian-at example setup:
* Create SpringBoot Application (like [so], do not mis the @EnableBlockumber annotation)
* run spring-boot
* navigate browser to "localhost:{server.port}/blockumber"
* be amazed

So far Blockumber assumes that cucumber tests can be run without robust external configuration, simply with providing glue location or additional external properties.

ToDos:
* ~~move controllers and stuff to blockumber-main and configure it using spring-boot annotations~~
* ~~figure out why it stops after finishing cucumber~~
* ~~display report~~
* ~~figure out how to determine step defs on runtime~~
* ~~convert stepdefs into blocks~~
* ~~run scenario created from blocks~~
* ~~display report nicely~~
* ~~expand test scenarios~~
* saving scenarios
* loading scenarios
* make it more configuratble
* group stepdefs by source
* ~~write a proper readme~~

[blockly]: https://developers.google.com/blockly
[behavior-driven development]: https://en.wikipedia.org/wiki/Behavior-driven_development
[editor]: https://en.wikipedia.org/wiki/Microsoft_Notepad
[so]: https://github.com/RemboL/blockumber/blob/master/librarian-at/src/main/java/pl/rembol/librarian/at/Application.java