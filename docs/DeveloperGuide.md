---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* {list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document can be found in the [diagrams](https://github.com/se-edu/addressbook-level3/tree/master/docs/diagrams/) folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

<img alt="Architecture Diagram" src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** has two classes called [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java). It is responsible for,
* At app launch: Initializes the components in the correct sequence, and connects them up with each other.
* At shut down: Shuts down the components and invokes cleanup methods where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

The rest of the App consists of four components.

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.


**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<img alt="Architecture Sequence Diagram" src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img alt="Component Managers" src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550" alt="Logic Class Diagram"/>

How the `Logic` component works:
1. When `Logic` is called upon to execute a command, it uses the `AddressBookParser` class to parse the user command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `AddCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to add a person).
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

The Sequence Diagram below illustrates the interactions within the `Logic` component for the `execute("delete 1")` API call.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img alt="Parser Classes" src="images/ParserClasses.png" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

There is also another noteworthy Logic class, `PersonAdapter`, that serves as a wrapper for the Model class `Person`. 
The key differences are that `Person` is immutable and does not support edits, while the `PersonAdapter` effectively supports edits by wrapping a single `Person` object and replacing it with an edited copy as and when necessary.
Such an implementation supports the user viewing and controlling a single client like with the `ViewCommand`.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img alt="Model Class Diagram" src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* stores `Storage` object and communicates with it to save address book to user files.

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img alt="Better Model Class Diagram" src="images/BetterModelClassDiagram.png" width="450" />

</div>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img alt="Storage Class Diagram" src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,
* can save both address book data and user preference data in json format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* Financial Advisor
* Has a need to manage a significant number of contacts
  * Keep track of their financial and personal information
* Prefer desktop apps over other types
* Tech-savvy, comfortable with keyboard shortcuts (CLI apps)
* Can type fast
* Prefers typing to mouse interactions


**Value proposition**: manage customers faster than a typical mouse/GUI driven app

The product provides financial advisors with a clean, easy to use interface to prepare 
them for meetings and maintain good relationships with their clients. On a per user basis 
it keeps track and displays customer’s financial details, their personal details, and 
presents upcoming events and todo lists. In the main page, it collates tasks and events 
for easy access.

The product will not help them with work relations with other Financial Advisors as the 
product’s scope only covers the personal use of the product. It does not link with any 
financial calculators, financial databases or cover policy / assets / market information.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                  | I want to …​               | So that I can…​                                                     |
| -------- | ------------------------------------------ | ------------------------------ | ---------------------------------------------------------------------- |
| `* * *`  | new user                                   | see usage instructions         | refer to instructions when I forget how to use the App                 |
| `* * *`  | user                                       | add a new client               | add new clients to my list                                             |
| `* * *`  | user                                       | delete a client                | remove clients that I no longer have                                   |
| `* * *`  | user                                       | find a client by name          | locate details of clients without having to go through the entire list |
| `* * *`  | user                                       | edit client's personal info    | access and update client personal information                          |
| `* * *`  | user                                       | edit client's financial info   | access and update client financial information                         |
| `* * *`  | user                                       | add meeting entry for clients  | track my client meetings                                               |
| `* * *`  | user                                       | delete meeting entry of clients| remove meetings that are no longer valid                               |
| `* * *`  | user                                       | view meeting entries associated with any client | access all meeting info of my clients                 |
| `* * *`  | user                                       | update existing meeting entries with corrections to timings or meeting notes |keep track of all meetings|
| `* * *`  | user                                       | hide and show my completed tasks | keep my todo list uncluttered and view uncompleted tasks             |
| `* * *`  | user                                       | create todo tasks              | add todo tasks                                                         |
| `* * *`  | user                                       | view todo tasks                | see all my tasks                                                       |
| `* * *`  | user                                       | update todo tasks              | make changes to upcoming tasks                                         |
| `* * *`  | user                                       | delete todo tasks              | remove tasks that are no longer needed                                 |
| `* * *`  | user with many clients in the address book | have a overview page that lists all todos / meetings coming up | Can be organised and easily see all tasks across all of my clients |
| `* * *`  | user with many clients in the address book | quickly jump to the client when clicking on an upcoming todo/meeting | Easily see accompanying details (financial / personal) when working on a specific task |
| `* *`    | user                                       | be able to create special dates that have notifications for reminders|set reminders for special occasions |
| `* *`    | user                                       | hide private contact details   | minimize chance of someone else seeing them by accident                |
| `* *`    | user                                       | view free time slots that take into account all existing meetings | easily pick out a new meeting slot based on free times. |
| `*`      | user with many clients in the address book | sort clients by name           | locate a client easily                                                 |
| `*`      | frequent user with many clients            | get a notification when a meeting / todo deadline is X days away | be reminded of upcoming tasks / meetings. |
| `*`      | new user                                   | follow a tutorial when adding my first client | learn how to add a new client                           |
| `*`      | new user                                   | follow a tutorial when editing a client       | learn how to update clients' personal / finance info    |
| `*`      | new user                                   | follow a tutorial when creating my first meeting | learn how to CRUD meeting / todo objects             |


### Use cases

(For all use cases below, the **System** is the `DonnaFin` application and the **Actor** is the `user`, unless specified otherwise)

**UC01: Adding a contact to DonnaFin**

**MSS**

1.  User chooses to add contact along with the relevant details
2.  DonnaFin announces that the contact has been successfully added.\
    Use Case ends.

**Extensions**
* 1a. The user types the command using the wrong syntax.
  * 1a1. DonnaFin shows an error message.\
         Use Case resumes from step 1. 

**UC02: Deleting a contact from DonnaFin**

**MSS**

1. User requests to delete a contact from DonnaFin using the right syntax.
2. DonnaFin announces that the contact has been successfully deleted.\
Use case ends.

**Extensions**

* 1a. The given index is invalid.

    * 1a1. DonnaFin shows an error message.

      Use case resumes from step 1.

**UC03: Finding a contact by name**

**MSS**
1. User chooses to find a contact within DonnaFin using the right syntax.
2. DonnaFin displays the contacts that match the keyword inputted.

**Extensions**
* 1a. The user types the command using the wrong syntax.
  * 1a1. DonnaFin shows an error message.\
         Use Case resumes at step 1.
* 1b. The keyword does not match any contacts.
  * 1b1. DonnaFin does not display any contact.\
         Use Case ends.

**UC04: Viewing the details of a contact**

**MSS**
1. User requests to view a contact using the right syntax.
2. DonnaFin displays details on the client.

**Extensions**
* 1a. The user types the command using the wrong syntax.
    * 1a1. DonnaFin shows an error message.\
      Use Case resumes at step 1.
* 1b. The given index is invalid.
    * 1b1. DonnaFin shows an error message.\
      Use Case resumes at step 1.
* 2a. The user selects a particular attribute of the client and edits it (valid input).
    * 2a1. Save the changes and display details again.\
      Use case resumes at step 2.
* 2b. The user selects a particular attribute of the client and edits it (invalid input).
    * 2b1. DonnaFin displays error and does not save.\
      Use case resumes at step 2.

**UC05: Getting help**

**MSS**
1. User requests for help to get assistance on commands.
2. DonnaFin displays a window with the user guide for the DonnaFin application.
 
**UC06: List**

**MSS**
1. User requests for the list of all the registered contacts.
2. DonnaFin displays all the contacts that has been registered within DonnaFin.

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `11` or above installed.
2.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  A novice with no coding background should be able to use the address book. 
5.  The system should respond within 100 milliseconds.
6.  The size of the application should be no more than 100 MB.
7.  All data should be stored locally.
8.  The application should not need a remote server to function.
9.  The application should not require any installer to start functioning.
10. The GUI should appear fine for screen resolutions 1920x1080 and higher.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, OS-X
* **Private contact detail**: A contact detail that is not meant to be shared with others

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
