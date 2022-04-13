# expense-reimbursement-system

## Project Description
The Expense Reimbursement System (ERS) will manage the process of reimbursing employees for expenses incurred while on company time. All employees in the company can log in and submit requests for reimbursement and view their past tickets and pending requests. Finance managers can log in and view all reimbursement requests and history for all employees in the company. Finance managers are authorized to approve and deny requests for expense reimbursement.

## Technologies Used
* HTML5
* CSS3
* JavaScript
* PostgreSQL 14
* AJAX
* Java 8
* JDBC
* Javalin 4.4.0
* Jupiter 5.8.1
* Mockito 4.4.0
* JWT
* Jackson databind 2.12.4

## Features
* As a logged-in employee, I can submit reimbursement requests.
* As a logged-in employee, I can upload a document or image of my receipt when submitting reimbursements.
* As a logged-in employee, I can view my past reimbursement requests.
* As a logged-in employee, I can view the reimbursement status as pending, approved or denied.
* As a logged-in manager, I can view all reimbursement requests and history for all employees in the company.
* As a logged-in manager, I can filter requests by status.
* As a logged-in manager, I can approve or deny a reimbursement request.

## Getting Started
1. Use the Command Line to create a new folder `mkdir<folder-name>` on your local machine to save the front-end and back-end repositories.  
2. Navigate to you new folder using `cd <folder-name>` and clone this repo using `git clone <repo-link>`.
3. Clone the [frontend repo](https://github.com/ankurshahi80/expense-reimbursement-system-front-end) in the same folder using `git clone <repo-link>`
4. Make sure you have have the following tools installed on your local machine.
  - [Visual Studio (VS) Code](https://code.visualstudio.com/)
  - [Java SE Development Kit 8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)  
    - Install the JDK on macOS  
      - First you must [create an Oracle account](https://docs.oracle.com/en/cloud/get-started/subscriptions-cloud/csgsg/get-oracle-com-account.html). When you have an account with Oracle, complete the following steps.
        1. Navigate to the [JDK download page](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html).
        2. Select the "mac OS x64" download option.
        3. Click the user agreement and scroll to the bottom, then check the box to accept the agreement.
        4. Sign in to your Oracle account.
        5. When the download is complete, open the installation files package.
        6. When the installation package window pops up, double-click the package icon to launch the install wizard.
        7. In the Introduction section of the install wizard, click Continue.
        8. The Destination Select section should automatically choose an installation location and redirect you to the next section.
        9. In the Installation Type section, click Install.
        10. Enter your password in the pop-up prompt, and click Install Software.
        11. When the installation is complete, you should see a message that says, "The software was successfully installed."
        12. You'll be automatically directed to the Summary Section, which means that you've successfully installed Java!
        13. Click the close button, and select "Move to Trash" for the installation files.
        14. Open Terminal and run the command `java -version` to check that the installation completed successfully. You can also verify this by opening System Preferences and looking for a Java icon there, as shown in the following image:  
        <img height="200" alt="image" src="https://user-images.githubusercontent.com/79622822/163214046-56d0c809-279a-4f7c-9413-18f3db588cc6.png">

    - Install the JDK on Windows
      - First you must [create an Oracle account](https://docs.oracle.com/en/cloud/get-started/subscriptions-cloud/csgsg/get-oracle-com-account.html). When you have an account with Oracle, complete the following steps.
        1. Navigate to the [JDK download page](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html).
        2. Select the "Windows x86" download option.
        3. Click the user agreement and scroll to the bottom, then check the box accepting the agreement.
        4. Sign in to your Oracle account.
        5. Run the install file.
        6. On the first setup page, click Install, as shown in the following image:
          ![image](https://user-images.githubusercontent.com/79622822/163215367-dd559968-d995-4a0d-9da2-0abe858a6745.png)
        7. Select the install path and click next.
        8. On the "Change in License Terms" page, click OK.
        9. Once again, choose the installation directory.
        10. Click Next. When the Complete page appears, you've successfully installed Java!
  
  - [IntelliJ IDEA](https://www.jetbrains.com/idea/)
    - Install the IntelliJ IDEA Community Edition for macOS
      - Install IntelliJ IDEA to use as your code editor. Complete the following steps:
        1. Navigate to the [IntelliJ IDEA download page](https://www.jetbrains.com/idea/download/#section=mac).
        2. Download the Community Edition for macOS.
        3. Run the disk image file after it downloads.
        4. In the Finder window that pops up, drag the IntelliJ IDEA CE icon into your Applications folder, as shown in the following image:  
        ![image](https://user-images.githubusercontent.com/79622822/163222348-0415bc97-8807-42bd-912e-61b384e792d9.png)
        5. In the Applications folder, open IntelliJ in Launchpad.
        6. Click Open when prompted to open a third-party app downloaded from the internet.
        7. When prompted to import IntelliJ settings from a config or installation folder, choose the default "Do not import settings" option and click OK.
        8. The next step is left to your own discretion! You can either choose to send usage statistics or choose the "Don't send" option to keep from sharing data.
        9. Next you’ll be prompted to customize IntelliJ IDEA. You can choose to "Skip remaining and set defaults", or you can click through (to set the theme and so on) until you reach the "Start Using IntelliJ" button.
        10. Don't forget to eject the install drive and delete the IntelliJ IDEA disk image from your Downloads folder after successful installation.
    - Install the IntelliJ IDEA for Windows
      - Install IntelliJ IDEA to use as your code editor. Complete the following steps:
        1. Navigate to the [IntelliJ IDEA download page](https://www.jetbrains.com/idea/download/#section=windows).
        2. Download the Community Edition
        3. Run the install file.
        4. On the first setup page, choose Next.
        5. Select the destination folder and click Next.
        6. Mark the "Update PATH variable option", create the ".java" association, and click Next.
        7. Click Install.
        8. Choose your preferred option for when to reboot.
        9. Once you've, rebooted, launch IntelliJ.
        10. Accept the Privacy Policy and continue.
        11. The final step is left to your discretion. You can either choose to send usage statistics or choose the "Don't send" option to keep from sharing data.
  - [Gradle](https://gradle.org/releases/)
    - Install Gradle as your build tool for Java
      1. Navigate to the [Gradle website](https://gradle.org/releases/) and click on the latest "binary-only" download.
      2. Extract the contents of the ZIP file, and save it in a new folder called "gradle-7.4"
      3. Update the environment variables
        1. Search "edit the system environment variables" in Windows search box and select the corresponding option.  
        ![image](https://user-images.githubusercontent.com/79622822/163230802-03ad8a29-6867-431a-8404-284384ec4c1e.png)
        2. Select Environment Variables
        3. Find the path variable, select it and click edit. Add the path to the bin folder of the gradle folder.
        4. Open command line and type `gradle - version` to verify if you have successfully setup Gradle on your computer.
  - [Postgres](https://www.postgresql.org/download/)
    - Install PostgreSQL as your relational database management system.
      1. Navigate to the [Postgres website](https://www.postgresql.org/download/) and select the appropriate operating system.
      2. Click on "Download the installer".
      3. Select the appropriate latest download for your OS
      4. Run the install file.
      5. Choose a password.
      6. Keep the default port (5432) and begin the installation.
      7. When you reach the Completing the PostgreSQL Setup Wizard, uncheck "Launch Stack Builder at exit" and click Finish.
  - [DBeaver](https://dbeaver.io/download/)
    - Install DBeaver as your database administration tool.
      1. Navigate to [DBeaver download page](https://dbeaver.io/download/).
      2. Download the Community Edition for your OS.
      3. Run the install file.
      4. On the Choose Components page, uncheck Associate .SQL files and click Next.
      5. Complete the rest of the installation process.
5. Set up the ers database
    1. Launch DBeaver.
    2. Create ers Schema in postgres database as shown below:  
      ![image](https://user-images.githubusercontent.com/79622822/163238822-afd304d2-f021-472a-aefa-a9600b733812.png)
    3. Run the [SQL script] within the ers schema
      ![image](https://user-images.githubusercontent.com/79622822/163239603-59b2ff3c-0ef1-4e78-9ae0-5b4bfb5aeb79.png)
    4. Refresh the Schema to confirm that you have the ers tables created correctly.
6. Run the back-end repo uning IntelliJ
    1. Launch IntelliJ.
    2. On the Welcome page, select Open.
    3. Navigate to your folder and open the back-end repo.
    4. Wait for the files to be built.
    5. In the Project pane, go to build.gradle and click on the refresh icon to load all dependecies.
    6. Navigate to src/main/java/com.revature/main/Driver class
    7. Right click, then select Modify Run Configuration
      ![image](https://user-images.githubusercontent.com/79622822/163242072-c285762e-1701-426b-aa0e-ed7f132e8c16.png)
    8. Add the following Environment variables:  
    `db_url=jdbc:postgresql://localhost:5432/postgres?currentSchema=ers;`  
    `db_username=postgres;`  
    `db_password=<your-password>`
    9. Click Apply to save the changes. Relaunch the IntelliJ to ensure the changes are saved.
    10. Navgate back to the Driver class and click on the play icon to Run the main method. The main method will start your server.
7. Run the front-end repo using VS Code
    1. Launch VS Code.
    2. Go to File menu, select Open Folder.
    3. Navigate to your folder and open the front-end repo.
    5. Select index.html
    6. Right click and select Open in Other Browser, then select Google Chrome to launch the application
    ![image](https://user-images.githubusercontent.com/79622822/163244492-cfc08d29-59c1-4b2c-b259-13e0683d6233.png)
8. Use the following credentials to explore the system
    - Manager
      - username: johndoe
      - password: test123
    - Employee
      - username: janedoe
      - password: test123  
      

## Usage

- Login to get started!  
<img width="400" alt="Screen Shot 2022-04-13 at 2 22 58 PM" src="https://user-images.githubusercontent.com/79622822/163245994-b872b6f2-0240-40ec-8d5b-b819c6f075b4.png">

- Submit a new reimbursement request  
<img width="400" alt="Screen Shot 2022-04-13 at 2 24 15 PM" src="https://user-images.githubusercontent.com/79622822/163246196-e1566da2-c262-4b7c-9b70-816364e9a552.png">

- Check status of pervious requests  
<img width="400" alt="Screen Shot 2022-04-13 at 2 23 57 PM" src="https://user-images.githubusercontent.com/79622822/163246386-1a2e4822-da9c-4975-b6aa-18b84872bbf8.png">

- Resolve reimbursement requests  
<img width="400" alt="Screen Shot 2022-04-13 at 2 24 38 PM" src="https://user-images.githubusercontent.com/79622822/163246620-fc419d1e-8c7d-4e60-bc8c-fe27ebf6714d.png">

## License
This project uses the following license: [MIT](https://choosealicense.com/licenses/mit/)
