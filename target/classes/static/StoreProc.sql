CREATE PROC GET_USER_BY_EMAIL
@email varchar(155)
AS
BEGIN
select * from users where email = @email;
END;


CREATE PROC GET_ROLE_BY_NAME
@name varchar(55)
As
BEGIN
select * from role where name =@name;
END;

CREATE PROC GET_USER_ROLE_ID_BY_ID
@userid int
As
BEGIN
select role_id from user_role where user_id =@userid;
END;

CREATE PROC GET_USER_ROLE_BY_ID
@userid int
As
BEGIN
select t1.* from role t1 inner join user_role t2 on t1.id=t2.role_id where user_id =@userid;
END;


CREATE PROC INSERT_UPDATE_USER
    @email varchar(155),
    @password varchar(155),
    @firstname varchar(155),
    @lastname varchar(155),
    @createby int,
    @updateby int,
    @id int = 0
    As
    IF  @id = 0
    BEGIN
    insert into users(email,password,first_name,last_name,create_by,update_by) values(@email,@password,@firstname,@lastname,@createby,@updateby);
    SELECT SCOPE_IDENTITY();
    END;
    ELSE
    BEGIN
    update users set password=@password,first_name=@firstname,last_name=@lastname,update_by=@updateby,update_time=GETDATE() where id=@id;
    END;


CREATE PROC INSERT_USER_ROLE
@userid int,
@roleid int,
@createby int,
@updateby int
AS
BEGIN
insert into user_role(user_id,role_id,create_by,update_by) values(@userid,@roleid,@createby,@updateby);
SELECT SCOPE_IDENTITY();
END;

CREATE PROC INSERT_UPDATE_CLIENT
@name varchar(155),
@code varchar(155),
@gstno varchar(155),
@email varchar(155),
@companyid int,
@mobile varchar(15),
@phone varchar(15),
@gccode int,
@cityid int,
@pincode int,
@address varchar(255),
@createby int,
@updateby int,
@id int = 0
As
IF  @id = 0
BEGIN
insert into client(name,code,gst_no,email,company_id,mobile,phone,gc_code,city_id,pin_code,address,create_by,update_by) values(@name,@code,
@gstno,@email,@companyid,@mobile,@phone,@gccode,@cityid,@pincode,@address,@createby,@updateby);
SELECT SCOPE_IDENTITY();
END;
ELSE
BEGIN
update client set name =@name,code=@code,gst_no=@gstno,email=@email,company_id=@companyid,mobile=@mobile,phone=@phone,gc_code=@gccode,city_id=@cityid,pin_code=@pincode,address=@address,update_by=@updateby,update_time=GETDATE() where id=@id;
END;

CREATE PROC INSERT_UPDATE_ITEM
@name varchar(155),
@longname varchar(155),
@code varchar(15),
@type varchar(1),
@unitm varchar(155),
@istaxable int,
@taxpercent decimal(3,2),
@createby int,
@updateby int,
@id int = 0
As
IF  @id = 0
BEGIN
insert into item(name,long_name,code,type,unit_measure,is_taxable,tax_percent,create_by,update_by) values(@name,@longname,@code,@type,@unitm,@istaxable,@taxpercent,@createby,@updateby);
SELECT SCOPE_IDENTITY();
END;
ELSE
BEGIN
update item set name=@name,long_name=@longname,code=@code,type=@type,unit_measure=@unitm,is_taxable=@istaxable,tax_percent=@taxpercent,update_by=@updateby,update_time=GETDATE() where id=@id;
END;


CREATE PROC INSERT_UPDATE_MODEL
@name varchar(155),
@code varchar(55),
@title1 text,
@title2 text,
@title3 text,
@title4 text,
@title5 text,
@title6 text,
@title7 text,
@title8 text,
@title9 text,
@title10 text,
@createby int,
@updateby int,
@id int = 0
As
IF  @id = 0
BEGIN
insert into model(name,code,title_1,title_2,title_3,title_4,title_5,title_6,title_7,title_8,title_9,title_10,create_by,update_by) values(@name,@code,@title1,@title2,@title3,@title4,@title5,@title6,@title7,@title8,@title9,@title10,,@createby,@updateby);
SELECT SCOPE_IDENTITY();
END;
ELSE
BEGIN
update model set name=@name,code=@code,title_1=@title1,title_2=@title2,title_3=@title3,title_4=@title4,
title_5=@title5,title_6=@title6,title_7=@title7,title_8=@title8,title_9=@title9,title_10=@title10,update_by=@updateby,update_time=GETDATE() where id=@id;
END;


CREATE PROC GET_ALL
@tablename varchar(55)
As
BEGIN
DECLARE @query NVARCHAR(MAX);
set @query = 'select * from ' +  QUOTENAME(@tablename)   ;
 Exec sp_executesql  @query
END;

CREATE PROC GET_BY_ID
@tablename varchar(55),
@id int
As
BEGIN
DECLARE @query NVARCHAR(MAX);
set @query = 'select * from ' + QUOTENAME(@tablename) + ' where id =@id ' ;
 Exec sp_executesql  @query,N'@id int',@id=@id
END;

CREATE PROC UPDATE_STATUS
@tablename varchar(55),
@status int,
@id int
As
BEGIN
DECLARE @query NVARCHAR(MAX);
set @query = 'update ' + QUOTENAME(@tablename) + ' set status = @status' + ' where id =@id ' ;
 Exec sp_executesql  @query,N'@status int,@id int',@status=@status,@id=@id
END;

CREATE PROC DELETE_BY_ID
@tablename varchar(55),
@id int
As
BEGIN
DECLARE @query NVARCHAR(MAX);
set @query = 'delete from ' + QUOTENAME(@tablename) + ' where id =@id ' ;
 Exec sp_executesql  @query,N'@id int',@id=@id
END;

CREATE PROC INSERT_UPDATE_COMPANY
@name varchar(155),
@code varchar(155),
@gstno varchar(155),
@email varchar(155),
@mobile varchar(15),
@phone varchar(15),
@cityid int,
@pincode int,
@address varchar(255),
@panno varchar(15),
@bankname varchar(155),
@branchname varchar(55),
@ifsccode varchar(15),
@accountno varchar(55),
@createby int,
@updateby int,
@id int = 0
As
IF  @id = 0
BEGIN
insert into company(name,code,gst_no,email,mobile,phone,city_id,pin_code,address,pan_no,bank_name,branch_name,ifsc_code,account_no,create_by,update_by)
values(@name,@code,@gstno,@email,@mobile,@phone,@cityid,@pincode,@address,@panno,@bankname,@branchname,@ifsccode,@accountno,@createby,@updateby);
SELECT SCOPE_IDENTITY();
END;
ELSE
BEGIN
update company set name=@name,code=@code,gst_no=@gstno,email=@email,mobile=@mobile,phone=@phone,city_id=@cityid,pin_code=@pincode,
address=@address,pan_no=@panno,bank_name=@bankname,branch_name=@branchname,ifsc_code=@ifsccode,account_no=@accountno,update_by=@updateby,update_time=GETDATE() where id =@id;
END;

CREATE PROC INSERT_UPDATE_CONTRACT
@companyid int,
@serialno int ,
@referenceno varchar(55),
@itemid int,
@buyerid int ,
@sellerid int ,
@modelid int ,
@rate float ,
@minquantity float ,
@maxquantity float,
@dispatchquantity float,
@buyerbrokeragerate float ,
@sellerbrokeragerate float,
@contractdate datetime2,
@contractreferral text ,
@createby int,
@updateby int,
@id int = 0
As
IF  @id = 0
BEGIN
insert into contract(company_id,serial_no,reference_no,item_id,buyer_id,seller_id,model_id,rate,min_quantity,
max_quantity,dispatch_quantity,buyer_brokerage_rate,seller_brokerage_rate,contract_date,contract_referral,create_by,update_by)
values(@companyid,@serialno,@referenceno,@itemid,@buyerid,@sellerid,@modelid,@rate,@minquantity,@maxquantity,
@dispatchquantity,@buyerbrokeragerate,@sellerbrokeragerate,@contractdate,@contractreferral,@createby,@updateby);
SELECT SCOPE_IDENTITY();
END;
ELSE
BEGIN
update contract set company_id=@companyid,serial_no=@serialno,reference_no=@referenceno,item_id=@itemid,buyer_id=@buyerid,seller_id=@sellerid,model_id=@modelid,
rate=@rate,min_quantity=@minquantity,max_quantity=@maxquantity,dispatch_quantity=@dispatchquantity,buyer_brokerage_rate=@buyerbrokeragerate,
seller_brokerage_rate=@sellerbrokeragerate,contract_date=@contractdate,contract_referral=@contractreferral,update_by=@updateby,update_time=GETDATE() where id =@id;
END;

