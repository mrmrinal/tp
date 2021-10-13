---
layout: page
title: Bharath's Project Portfolio Page
---

### Project: DonnaFin.io

DonnaFin.io is a desktop address book application used by financial advisors to keep track of their client information and related tasks.
This is a Java program with about 10 kloc with a JavaFX GUI.
The '.io' in our name is a reflection of our belief that you deserve a faster workflow for input and output. 
If you can type fast, you can use our CLI-like commands to manage and view your notes, upcoming meetings and todo lists much faster than your typical customer relationship manager apps.

Given below are my contributions to the project.

* **New Feature**: 
    * What it does:
    * Justification: 
    * Highlights:
    * Credits: *{mention here if you reused any code/ideas from elsewhere or if a third-party library is heavily used in the feature so that a reader can make a more accurate judgement of how much effort went into the feature}*

* Support passing instructions to `Ui` from `Commands`
  * Uses functional programming to hand a callback from the result of command execution (`CommandResult.uiAction : Consumer<Ui>`) that can perform the necessary side effect in `Ui`
  * Our application's front end is getting steadily more complex and our commands need to be able to execute actual functions on `Ui`. However, other solutions would require global variables or drilling down a reference to `Ui` into `Command`. A simpler and cleaner workaround was to use functions as a first class object to instruct `Ui` on the appropriate actions (show help / exit / show client view, etc)
  * Created when it was necessary to implement the `ClientView` mode in UI (Showing a single client's information in a dedicated view for the user to edit and interact with). [#67](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/67)

* Allow user to edit information directly in the Client View
  * The user must be able to cycle through the different attributes (presented in a row format), and edit as necessary.
    * Heavily refactor `ClientView` to support actual interactions with the `AttributePanel`s [#90](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/90)
    * Support user focus (highlight row when tabbing through) [#92](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/92)
    * Show user in an alert whenever edit fails, and for what field (e.g. put letters in the phone number field) [#95](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/95)
  * As this is a critical part of our functionality, we need this to be intuitive and easy to use.
  * Using `EventHandler`s to great effect within text fields to support highlighting outer parent elements.

* Re-organise application architecture by separating `Storage` and `Logic` [#18](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/18), [#17](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/17)
  * Improves overall architecture by decoupling `Storage` and `Logic` ensuring all `Storage` calls go through `Model` layer to follow the layered architecture style[^layeredArchi]
  * The layered architecture design pattern was chosen over the current Model-View-Controller architecture in a previous team meeting to reduce complexity, simplify working on components and improve testability.


[^layeredArchi]: [_O' Reilly_ Chapter 1. Layered Architecture](https://www.oreilly.com/library/view/software-architecture-patterns/9781491971437/ch01.html)
    
* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17&tabOpen=true&tabType=authorship&tabAuthor=bharathcs&tabRepo=AY2122S1-CS2103T-W16-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false)

* **Project management**:
  * Manage CI / CD to consistently check and ensure repo code quality and automated testing success
    * Have Gradle test commands run only if code / CI changes. [#1](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/1)
    * Conduct the minimal repo-wide checks for non-code changes (EOF, EOL, illegal characters) [#37](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/37)
    * Update Github CI / CD to support feature / branch workflow [#35](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/35)
  * Provided constructive feedback to PRs
    * Improving code quality [#69](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/69), [#57](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/57) 
    * Minor changes for CheckStyle & CI [#63](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/63)


* **Enhancements to existing features**:

  * Improve UI for user.
    * Enable responsive resizing of `AttributePanel` when window size changes in `ClientView` [#99](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/99)
    * Show the user a focus indicator when the tabs are being selected in `ClientView` [#101](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/101)

* **Documentation**:
    * User Guide:
      * Update commands [#27](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/27), [#42](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/42/)
      
    * Developer Guide:
      * Storage - Logic link updates: Sequence diagrams, Class Diagrams, [#21](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/21)
       

* **Community**:
  * Explaining why I think Java's Stream implementation makes using multiple Scanners very bug-prone ([#78](https://github.com/nus-cs2103-AY2122S1/forum/issues/78#issuecomment-908386678))
  * Clarifying the difference between `Platform.exit()` and `System.exit` in JavaFX ([#174](https://github.com/nus-cs2103-AY2122S1/forum/issues/174#issuecomment-912537867))
  * Optimising CI tasks to run only on Linux where helpful ([#200](https://github.com/nus-cs2103-AY2122S1/forum/issues/200#issuecomment-914049272))
  * Reminding people of checkstyle gaps ([#169](https://github.com/nus-cs2103-AY2122S1/forum/issues/169#issuecomment-914045772))
  * Offering a tip for people who like to make many commits ([#139](https://github.com/nus-cs2103-AY2122S1/forum/issues/139#issuecomment-908866902))

* **Tools**:
  * Used Figma to wireframe the user pathway to 'single client view'.

