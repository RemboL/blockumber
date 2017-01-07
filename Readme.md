# Blockumber

## What is?

Blockumber is an open source project that allows you to create cucumber scenarios not with text editor, but with [blockly] - hence the name, Blockly + Cucumber = Blockumber.

![Alt text](/blockumber.png?raw=true "Blockumber in action")

## Reasoning behind project

Most of developers, when they are first introduced to [behavior-driven development] are told that it's a gorgeous tool for bridging business requirements and automated tests - developers provide a set of step definitions (or glue) and business can create their own scenarios with provided glue.

Then most developers after few months or years of practice with BDD will tell you that it in most cases won't work like that.

In [90% of situations] reason is that business is either not elastic enough to understand the principles behind step definitions or is annoyed by the limitations of those definitions that are often hidden under provided scenario editor (though I had the pleasure of working with Product Owner who lied in the sweet spot between - but that might be due to the fact of her IT education).

I'll give you an example.

Product owner provides you with a scenario that he wants to test:
```
given a basket with 10 cucumbers
when I take 4 cucumbers
then I have 6 cucumbers left in the basket
```

The you develop your stepdefs accordingly:
```groovy
Given(~'a basket with (\\d+) cucumbers') { ... }
When(~'I take (\\d+) cucumbers') { ... }
Then(~'I have (\\d+) cucumbers left in the basket') { ... }
```
All is peachy, devs are happy because they automated verification for the oh-so-important cucumber/basket logic, PO's happy because he can now in his [editor] create scenarios that will run automatically.

But then PO starts to think about other scenarios:
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

Either way, this usually means that devs have more work with [greening up the pipelines] - debugging, coding additional stepdefs, etc., and (hopefully) at some point business notices that this work setup where they write scenarios is not cost effective and they give up.

But this course of action is the result of simple fact - business can do in the text editor much more than the stepdefs allow.

That's basically the reason behind this little project - to provide the editor that allows no more and no less than stepdefs allow (and also bright colors of blockly blocks should draw in the simple business minds).

## How it works?

The repo is divided into three modules
* librarian - an example spring-boot application that will be tested
* librarian-at - separate module containing cucumber scenarios (Why? Because in my experience in big projects' ATs like to migrate to separate module and be run against living application)
* blockumber-main - the meat behind it all

## How to use?

To set up, you need follow the librarian-at example.
 * Configure your build script to download proper dependency from jCenter:

``` gradle
repositories {
	jcenter()
}
dependencies {
    compile 'pl.rembol.blockumber:blockumber-main:0.0.3'
}
```

 * Create a bootRun main class in your project (or configure existing one):
``` java
@EnableBlockumber
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

}
```
 * Configure path to glue in your application properties:
``` properties
blockumber.glue=src/test/groovy
```

* run spring-boot
* navigate browser to "localhost:{server.port}/blockumber"
* be amazed

Basically, at startup, Blockumber uses Cucumber to load up stepdefs from provided glue path and uses them to create custom Blockly blocks. 

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
* ~~write a proper readme~~
* ~~saving scenarios~~
* ~~loading scenarios~~
* make it more configuratble
* group stepdefs by source
* ~~release it~~

[blockly]: https://developers.google.com/blockly
[behavior-driven development]: https://en.wikipedia.org/wiki/Behavior-driven_development
[90% of situations]: http://dilbert.com/strip/2008-05-08
[editor]: https://en.wikipedia.org/wiki/Microsoft_Notepad
[greening up the pipelines]: http://www.liquid-roof.com/images/painting2.jpg
