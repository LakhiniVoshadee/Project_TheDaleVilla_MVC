drop database if exists theDaleVilla;

create database theDaleVilla;

use theDaleVilla;

create table Admin(
     UserId varchar(20)primary key,
     UserName varchar(20)not null,
     Password varchar(20)not null,
     Mob_num varchar(20)
);

create table Employee(
     EmpID varchar(20)primary key,
     Name varchar(20)not null,
     Type varchar(20)not null,
     Email varchar(30)not null,
     DOB date
);

create table Salary(
     SalaryID varchar(20)primary key,
     Date date,
     Amount varchar(25),
     EmpID varchar(20)not null,
     foreign key(EmpID) references Employee(EmpID)on update cascade on delete cascade
);


create table Supplier(
      SupID varchar(20)primary key,
      Name varchar(20)not null,
      Mob_num int(20),
      Address varchar(30),
      UserID varchar(20)not null,
      foreign key(UserID) references Admin(UserID)on update cascade on delete cascade
);

create table Customer(
      CusID varchar(20)primary key,
      Name varchar(20)not null,
      sex varchar(20),
      NIC varchar(30)not null,
      Mob_num int(20),
      Email varchar(30)not null,
      UserID varchar(20)not null,
      foreign key(UserID) references Admin(UserID)on update cascade on delete cascade
);


create table Payment(
      PayID varchar(20)primary key,
      Date date,
      Description varchar(30),
      CusID varchar(20)not null,
      foreign key(CusID) references Customer(CusID)on update cascade on delete cascade
);


create table Food(
      FoodID varchar(20)primary key,
      Description varchar(30)
 );


create table FoodStock(
      FoodID varchar(20)not null,
      SupID varchar(20)not null,
      Date date,
      QtyOnHand int(30)not null,
      foreign key(FoodID) references Food(FoodID)on update cascade on delete cascade,
      foreign key(SupID) references Supplier(SupID)on update cascade on delete cascade
);



create table Room(
     RoomID varchar(20)primary key,
     Type varchar(30)not null,
     Date date,
     CusID varchar(20)not null,
     foreign key(CusID) references Customer(CusID)on update cascade on delete cascade
);



create table Event(
     EventID varchar(20)primary key,
     Type varchar(30),
     Description varchar(30)
);


create table EventDetail(
     CusID varchar(20)not null,
     EventID varchar(20)not null,
     Date date,
     Description varchar(30),
     foreign key(CusID) references Customer(CusID)on update cascade on delete cascade,
     foreign key(EventID) references Event(EventID)on update cascade on delete cascade
);


create table Rent(
    RentID varchar(20)primary key,
    Qty int(20)not null,
    Description varchar(30),
    Bycicle varchar(30),
    Surfing_Boards varchar(30)
 );



create table RentDetails(
    RentID varchar(20)not null,
    CusID varchar(20)not null,
    Date date,
    Description varchar(30),
    foreign key(RentID) references Rent(RentID)on update cascade on delete cascade,
    foreign key(CusID) references Customer(CusID)on update cascade on delete cascade
);