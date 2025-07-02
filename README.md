# ATM_Interface
# 💳 ATM Interface – Java Swing GUI Project

This is a **Java Swing-based GUI application** that simulates a basic ATM interface. It allows users to register, log in, check balances, deposit, withdraw money, view transaction history, and update their PINs. All user data is stored and managed in an **Excel file** using the Apache POI library.

## 🛠 Features

- 🔐 **User Login & Registration**
- 💰 **Check Balance**
- ➕ **Deposit Money**
- ➖ **Withdraw Money** (with daily withdrawal limit)
- 📋 **Transaction History**
- 🔄 **Change PIN**
- 💾 **Excel-based Storage** using Apache POI (`users.xlsx`)

## 🧰 Technologies Used

- **Java SE (Swing)**
- **Apache POI** (for Excel file manipulation)
- **JDK 8 or above**

## Download Apache POI JARs
##Download Apache POI from here and add these required JARs to your classpath:

poi-xxx.jar
poi-ooxml-xxx.jar
poi-ooxml-schemas-xxx.jar
xmlbeans-xxx.jar
commons-collections4-xxx.jar

Or copy all JARs into a /lib folder and include them when compiling.

## Compile the Project

javac -cp ".;lib/*" ATMGUI.java
On macOS/Linux, replace ; with :
javac -cp ".:lib/*" ATMGUI.java

## Run the Program

java -cp ".;lib/*" ATMGUI
