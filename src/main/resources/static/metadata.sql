INSERT INTO country(name) values('India');

INSERT INTO state (parent_id,name)
VALUES
( 1, 'Andaman & Nicobar Islands' ),
( 1, 'Andhra Pradesh' ),
( 1, 'Arunachal Pradesh' ),
( 1, 'Assam' ),
( 1, 'Bihar' ),
( 1, 'Chandigarh' ),
( 1, 'Chhattisgarh' ),
( 1, 'Dadra & Nagar Haveli' ),
( 1, 'Daman & Diu' ),
( 1 , 'Delhi' ),
( 1 , 'Goa' ),
( 1 , 'Gujarat' ),
( 1 , 'Haryana' ),
( 1 , 'Himachal Pradesh' ),
( 1 , 'Jammu & Kashmir' ),
( 1 , 'Jharkhand' ),
( 1 , 'Karnataka' ),
( 1 , 'Kerala' ),
( 1 , 'Lakshadweep' ),
( 1 , 'Madhya Pradesh' ),
( 1 , 'Maharashtra' ),
( 1 , 'Manipur' ),
( 1 , 'Meghalaya' ),
( 1 , 'Mizoram' ),
( 1 , 'Nagaland' ),
( 1 , 'Odisha' ),
( 1 , 'Puducherry' ),
( 1 , 'Punjab' ),
( 1 , 'Rajasthan' ),
( 1 , 'Sikkim' ),
( 1 , 'Tamil Nadu' ),
( 1 , 'Telangana' ),
( 1 , 'Tripura' ),
( 1 , 'Uttar Pradesh' ),
( 1 , 'Uttarakhand' ),
( 1 , 'West Bengal' );


insert into users(email,password,first_name,last_name) values('admin','admin','admin','admin');
insert into role(name,create_by,update_by) values('ROLE_ADMIN',1,1);
insert into role(name,create_by,update_by) values('ROLE_USER',1,1);