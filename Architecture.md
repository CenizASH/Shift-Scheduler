### Shift Scheduler Architecture

Shift Scheduler is an Android application designed to help managers efficiently schedule and manage employee shifts, taking into account employee availability. The app features employee caching, monthly work statistics, an advanced scheduling algorithm, and more to improve workplace productivity and collaboration.

The application is organized into several packages, each responsible for different functionalities. Below is a brief description of each package and its major source code files.

## 1) APPLICATION CONTROLLER
MyApplication.java <br />
NotificationReceiver.java <br />
   
   
## 2) PRESENTATION/ GUI:
AvailabilityActivity.java <br />
AvailabilityDialogFragment.java <br />
CalenderView.java <br />
EmployeeDetailActivity.java <br />
GlanceActivity.java <br />
LoginActivity.java <br />
MainActivity.java <br />
ManageEmployeesActivity.java <br />
RegisterActivity.java <br />
StatisticsActivity.java <br />

   
## 3) BUSINESS/ LOGIC:
EmployeManager.java <br />
AvailabilityManager.java <br />
AccountManager.java <br />
StatisticsManager.java <br />

   
## 4) PERSISTENCE/ STORAGE:
AccountDao.java <br />
AccountDaoHSQLDB.java <br />
AccountDaoStub.java <br />
AvailabilityDao.java <br />
AvailabilityDaoHSQLDB.java <br />
AvailabilityDaoStub.java <br />
DatabaseHelper.java <br />
EmployeeDAOHSQLDB.java <br />
EmployeeDao.java <br />
EmployeeDaoStub.java <br />


   
## 5) OBJECTS
Account.java <br />
Availability.java <br />
Employee.java <br />
Statistics.java <br />