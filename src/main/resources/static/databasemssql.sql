
DROP TABLE IF EXISTS [users] ; 
 CREATE TABLE users (
   [id] int NOT NULL IDENTITY,
   [email] varchar(155) NOT NULL,
   [password] varchar(155) NOT NULL,
   [first_name] varchar(155) NOT NULL,
   [last_name] varchar(155) NOT NULL,
   [status]  smallint DEFAULT 1,
   [create_time] datetime2(0) NOT NULL DEFAULT GETDATE(),
   [update_time] datetime2(0) NOT NULL DEFAULT GETDATE(),
   [create_by] int DEFAULT NULL,
   [update_by] int DEFAULT NULL,
   PRIMARY KEY ([id])
 )   ;

 DROP TABLE IF EXISTS [role] ; 
 CREATE TABLE role (
    [id] int NOT NULL IDENTITY,
    [name] varchar(55) NOT NULL,
    [status]  smallint DEFAULT 1,
    [create_time] datetime2(0) NOT NULL DEFAULT GETDATE(),
    [update_time] datetime2(0) NOT NULL DEFAULT GETDATE(),
    [create_by] int NOT NULL,
    [update_by] int NOT NULL,
    PRIMARY KEY ([id]),
    CONSTRAINT [create_by_key_role] FOREIGN KEY ([create_by]) REFERENCES users ([id]),
    CONSTRAINT [update_by_key_role] FOREIGN KEY ([update_by]) REFERENCES users ([id])
  );

CREATE INDEX [create_by_idx] ON role ([create_by]);
CREATE INDEX [update_by_idx] ON role ([update_by]);

 DROP TABLE IF EXISTS [user_role] ; 
 CREATE TABLE user_role (
    [id] int NOT NULL IDENTITY,
    [user_id] int NOT NULL,
    [role_id] int NOT NULL,
    [status] smallint DEFAULT '1',
    [create_time] datetime2(0) NOT NULL DEFAULT GETDATE(),
    [update_time] datetime2(0) NOT NULL DEFAULT GETDATE(),
     [create_by] int NOT NULL,
     [update_by] int NOT NULL,
    PRIMARY KEY ([id]),
    CONSTRAINT [user_role_user_id] FOREIGN KEY ([user_id]) REFERENCES users ([id]),
    CONSTRAINT [user_role_role_id] FOREIGN KEY ([role_id]) REFERENCES role ([id]),
    CONSTRAINT [user_role_create_by] FOREIGN KEY ([create_by]) REFERENCES users ([id]),
    CONSTRAINT [user_role_update_by] FOREIGN KEY ([update_by]) REFERENCES users ([id])
  )  ;

CREATE INDEX [role_id_idx] ON user_role ([role_id]);
CREATE INDEX [user_id_key_idx] ON user_role ([user_id]);
CREATE INDEX [create_by_idx] ON user_role ([create_by]);
CREATE INDEX [update_by_idx] ON user_role ([update_by]);

DROP TABLE IF EXISTS [country] ;
 CREATE TABLE country (
   [id] int NOT NULL IDENTITY,
   [name] varchar(55) NOT NULL,
   [code] varchar(10)  DEFAULT NULL,
   [status]  smallint DEFAULT 1,
   [create_time] datetime2(0) NOT NULL DEFAULT GETDATE(),
   [update_time] datetime2(0) NOT NULL DEFAULT GETDATE(),
   [create_by] int NOT NULL DEFAULT 1,
   [update_by] int NOT NULL DEFAULT 1,
   PRIMARY KEY ([id])
 );

 DROP TABLE IF EXISTS [state] ;
  CREATE TABLE state (
    [id] int NOT NULL IDENTITY,
    [name] varchar(55) NOT NULL,
    [code] varchar(10)  DEFAULT NULL,
    [parent_id] int NOT NULL,
    [status]  smallint DEFAULT 1,
    [create_time] datetime2(0) NOT NULL DEFAULT GETDATE(),
    [update_time] datetime2(0) NOT NULL DEFAULT GETDATE(),
    [create_by] int NOT NULL DEFAULT 1,
    [update_by] int NOT NULL DEFAULT 1,
    PRIMARY KEY ([id]),
    CONSTRAINT [state_parent_id_key] FOREIGN KEY ([parent_id]) REFERENCES country ([id])
  );
CREATE INDEX [parent_id_idx] ON state ([parent_id]);

   DROP TABLE IF EXISTS [city] ;
    CREATE TABLE city (
      [id] int NOT NULL IDENTITY,
      [name] varchar(55) NOT NULL,
      [code] varchar(10)  DEFAULT NULL,
      [parent_id] int NOT NULL,
      [status]  smallint DEFAULT 1,
      [create_time] datetime2(0) NOT NULL DEFAULT GETDATE(),
      [update_time] datetime2(0) NOT NULL DEFAULT GETDATE(),
      [create_by] int NOT NULL DEFAULT 1,
      [update_by] int NOT NULL DEFAULT 1,
      PRIMARY KEY ([id]),
      CONSTRAINT [city_parent_id_key] FOREIGN KEY ([parent_id]) REFERENCES state ([id])
    );
 CREATE INDEX [parent_id_idx] ON city ([parent_id]);


DROP TABLE IF EXISTS [company] ;
 CREATE TABLE company (
   [id] int NOT NULL IDENTITY,
   [name] varchar(55) NOT NULL,
   [code] varchar(10) DEFAULT NULL,
   [gst_no] varchar(25) DEFAULT NULL,
   [email] varchar(55) DEFAULT NULL,
   [phone] varchar(15)  DEFAULT NULL,
   [mobile] varchar(15) DEFAULT NULL,
   [city_id] int DEFAULT NULL,
   [pin_code] int DEFAULT NULL,
   [address] varchar(255) DEFAULT NULL,
   [pan_no] varchar(15) DEFAULT NULL,
   [bank_name] varchar(155) DEFAULT NULL,
   [branch_name] varchar(55) DEFAULT NULL,
   [ifsc_code] varchar(55) DEFAULT NULL,
   [account_no] varchar(55) DEFAULT NULL,
   [status]  smallint DEFAULT 1,
   [create_time] datetime2(0) NOT NULL DEFAULT GETDATE(),
   [update_time] datetime2(0) NOT NULL DEFAULT GETDATE(),
   [create_by] int NOT NULL,
   [update_by] int NOT NULL,
   PRIMARY KEY ([id]),
   CONSTRAINT [company_update_by] FOREIGN KEY ([update_by]) REFERENCES users ([id]),
   CONSTRAINT [company_create_by] FOREIGN KEY ([create_by]) REFERENCES users ([id])
 );


DROP TABLE IF EXISTS [client] ;
 CREATE TABLE client (
   [id] int NOT NULL IDENTITY,
   [company_id] int DEFAULT NULL,
   [name] varchar(55) NOT NULL,
   [code] varchar(10) DEFAULT NULL,
   [gst_no] varchar(25) DEFAULT NULL,
   [gc_code] int DEFAULT NULL,
   [email] varchar(55) DEFAULT NULL,
   [phone] varchar(15)  DEFAULT NULL,
   [mobile] varchar(15) DEFAULT NULL,
   [city_id] int DEFAULT NULL,
   [pin_code] int DEFAULT NULL,
   [address] varchar(255) DEFAULT NULL,
   [status]  smallint DEFAULT 1,
   [create_time] datetime2(0) NOT NULL DEFAULT GETDATE(),
   [update_time] datetime2(0) NOT NULL DEFAULT GETDATE(),
   [create_by] int NOT NULL,
   [update_by] int NOT NULL,
   PRIMARY KEY ([id]),
   CONSTRAINT [client_create_by] FOREIGN KEY ([update_by]) REFERENCES users ([id]),
   CONSTRAINT [client_update_by] FOREIGN KEY ([create_by]) REFERENCES users ([id])
 );
CREATE INDEX [parent_id_idx] ON client ([company_id]);



 DROP TABLE IF EXISTS [item] ;
  CREATE TABLE item (
    [id] int NOT NULL IDENTITY,
    [name] varchar(55) NOT NULL,
    [long_name] varchar(55) DEFAULT NULL,
    [unit_measure] varchar(10) DEFAULT NULL,
    [code] varchar(15) DEFAULT NULL,
    [is_taxable] smallint NOT NULL DEFAULT 0,
    [tax_percent] DECIMAL(3,2)  DEFAULT NULL,
    [type] char(1) DEFAULT NULL,
    [status]  smallint DEFAULT 1,
    [create_time] datetime2(0) NOT NULL DEFAULT GETDATE(),
    [update_time] datetime2(0) NOT NULL DEFAULT GETDATE(),
    [create_by] int NOT NULL,
    [update_by] int NOT NULL,
    PRIMARY KEY ([id]),
    CONSTRAINT [item_update_by] FOREIGN KEY ([update_by]) REFERENCES users ([id]),
    CONSTRAINT [item_create_by] FOREIGN KEY ([create_by]) REFERENCES users ([id])
  );

  DROP TABLE IF EXISTS [model] ;
    CREATE TABLE model (
      [id] int NOT NULL IDENTITY,
      [name] varchar(55) NOT NULL,
      [code] varchar(15) DEFAULT NULL,
      [title_1] text DEFAULT NULL,
      [title_2] text DEFAULT NULL,
      [title_3] text DEFAULT NULL,
      [title_4] text DEFAULT NULL,
      [title_5] text DEFAULT NULL,
      [title_6] text DEFAULT NULL,
      [title_7] text DEFAULT NULL,
      [title_8] text DEFAULT NULL,
      [title_9] text DEFAULT NULL,
      [title_10] text DEFAULT NULL,
      [status]  smallint DEFAULT 1,
      [create_time] datetime2(0) NOT NULL DEFAULT GETDATE(),
      [update_time] datetime2(0) NOT NULL DEFAULT GETDATE(),
      [create_by] int NOT NULL,
      [update_by] int NOT NULL,
      PRIMARY KEY ([id]),
      CONSTRAINT [model_update_by] FOREIGN KEY ([update_by]) REFERENCES users ([id]),
      CONSTRAINT [model_create_by] FOREIGN KEY ([create_by]) REFERENCES users ([id])
    );

DROP TABLE IF EXISTS [contract] ;
    CREATE TABLE contract (
      [id] int NOT NULL IDENTITY,
      [serial_no] int NOT NULL,
      [reference_no] varchar(55) NOT NULL,
      [company_id] int NOT NULL,
      [item_id] int NOT NULL,
      [buyer_id] int NOT NULL,
      [seller_id] int NOT NULL,
      [model_id] int DEFAULT NULL,
      [rate] float DEFAULT NULL,
      [min_quantity] float DEFAULT NULL,
      [max_quantity] float DEFAULT NULL,
      [dispatch_quantity] float DEFAULT NULL,
      [buyer_brokerage_rate] float DEFAULT NULL,
      [seller_brokerage_rate] float DEFAULT NULL,
      [contract_date] datetime2(0) DEFAULT NULL,
      [contract_referral] text DEFAULT NULL,
      [status]  smallint DEFAULT 1,
      [create_time] datetime2(0) NOT NULL DEFAULT GETDATE(),
      [update_time] datetime2(0) NOT NULL DEFAULT GETDATE(),
      [create_by] int NOT NULL,
      [update_by] int NOT NULL,
      PRIMARY KEY ([id]),
      CONSTRAINT [contract_update_by] FOREIGN KEY ([update_by]) REFERENCES users ([id]),
      CONSTRAINT [contract_create_by] FOREIGN KEY ([create_by]) REFERENCES users ([id])
    );











