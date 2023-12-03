Download the project file or zip file (extract zip if downloaded that way)

Make sure Java is installed on your device before proceeding

Open the command prompt and enter the "xampp" directory, then enter "mysql_start"

Open a new command prompt and enter the directory "xampp/mysql/bin"

Enter the command "mysql -h localhost -u root"

Follow with the command "grant all privileges on * to user@localhost identified by 'password' with grant option;"
Change user to the username you want to use and password should also be changed to the password you want (Keep the single quotes around password)

Enter "commit;"

Next enter "create database checkers_space;"

Enter "grant all on checkers_space.* to user identified by 'password';"
Again user and and password should be the username and password you entered before

Now you can exit by entering "exit"

Next log into the account you made by entering "mysql -h localhost -u user -p"
user is your username

It will ask for your password so enter it and hit enter

Then enter "use checkers_space;"

Next run the command "source c:path"
path should be the path to the project file downloaded at the start and then to the sql file inside that folder
path should end with "/createCheckersTable.sql"

Now we are ready to run the bat files to play the game

Make sure that Firewalls are turned off for this portion, otherwise you cannot connect two computers

Enter into the project file




















