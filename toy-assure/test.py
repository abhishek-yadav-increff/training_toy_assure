# Project for Instant Message Services using Python and MySQL Connectivity
# Programmed by Adrika Yadav- DAV Public School Kusmunda, Korba

import os
import mysql.connector

mydb = mysql.connector.connect(
    host="localhost", user="root", passwd="tiger", database="service"
)


def create_table():
    try:
        mycursor = mydb.cursor()
        mycursor.execute("CREATE DATABASE service")
        mycursor = mydb.cursor()
        mycursor.execute(
            "CREATE TABLE ims (msg_id VARCHAR(10),rname VARCHAR(30),sname VARCHAR(30),rmail VARCHAR(50),smail VARCHAR(50),msg VARCHAR(250))"
        )
        print("Table Created")
    except:
        print("Databse or Table Already Created")


def add_msg():
    mycursor = mydb.cursor()
    msg_id = input("Enter Messae ID: ")
    rname = input("Enter the Name of Receiver: ")
    sname = input("Enter the Name of Sender: ")
    rmail = input("Enter Receiver E-mail : ")
    smail = input("Enter the Sender E-mail : ")
    msg = input("Enter the Message : ")
    sql = (
        "INSERT INTO ims(msg_id,rname,sname,rmail,smail,msg) VALUES (%s,%s,%s,%s,%s,%s)"
    )
    val = (msg_id, rname, sname, rmail, smail, msg)
    mycursor.execute(sql, val)
    mydb.commit()
    print(mycursor.rowcount, "Record inserted.")


def search_msg():
    mycursor = mydb.cursor()
    print("Select the search criteria : ")
    print("1. Message ID: ")
    print("2. Name of Sender: ")
    print("3. Name of Receiver: ")
    print("4. All Details")
    ch = int(input("Enter the choice : "))

    if ch == 1:
        s = input("Enter Message ID : ")
        mycursor.execute("SELECT * FROM ims")
        myresult = mycursor.fetchall()
        for x in myresult:
            if x[0] == s:
                print(x)

        if myresult:
            print("No Record Found for ID: ", s)

    elif ch == 2:
        sn = input("Enter Sender Name : ")
        mycursor.execute("SELECT * FROM ims")
        myresult = mycursor.fetchall()
        for x in myresult:
            if x[2] == s:
                print(x)
        if myresult:
            print("No Record Found for Sender Name: ", s)

    elif ch == 3:
        s = input("Enter Receiver Name : ")
        mycursor.execute("SELECT * FROM ims")
        myresult = mycursor.fetchall()
        for x in myresult:
            if x[1] == s:
                print(x)
        if myresult:
            print("No Record Found for Receiver Name: ", s)

    elif ch == 4:
        mycursor.execute("SELECT * FROM ims")
        myresult = mycursor.fetchall()
        for x in myresult:
            print(x)
        if myresult:
            print("No Record Found !!")


def delete_msg():
    mycursor = mydb.cursor()
    ms = input("Enter the Message ID to be deleted : ")
    sql = "DELETE FROM ims WHERE msg_id = %s" % ms
    mycursor.execute(sql)
    mydb.commit()
    print(mycursor.rowcount, "record(s) deleted")


def Main_Menu():
    print("Enter 1 : TO ADD NEW MESSAGE")
    print("Enter 2 : TO SEARCH MESSAGE")
    print("Enter 3 : TO DELETE MESSAGE")
    print("Enter others to Exit")
    try:
        userInput = int(input("Please Select An Above Option: "))

        if userInput == 1:
            print("\n")
            add_msg()
        elif userInput == 2:
            search_msg()
        elif userInput == 3:
            delete_msg()
        else:
            print("Enter correct choice. . . ")
            ch = input("\nwant to continue Y/N: ")
        if ch == "Y" or ch == "y":
            print(os.system("cls"))
            Main_Menu()
        else:
            print("Program going to Exit")
            exit("\n! Thanks")
    except:
        print("Something Wrong in code")


# Main_Menu()
create_table()
