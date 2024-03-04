
insert into users_roles (id, user_role)
values (1,  'ADMIN'), (2, 'USER');

insert into users (id, email, first_name, is_active, last_name, password, image_url )
values (1, 'admin@abv.bg', 'georgi', 1, 'admin', '383392396be39c421e7928f5789f090357f77664b1bf3c091c228fe75ca85e5599bff4633cee0792', null ),
       (2, 'user@abv.bg', 'georgi', 1, 'user', '383392396be39c421e7928f5789f090357f77664b1bf3c091c228fe75ca85e5599bff4633cee0792', null );
--                                               pass:kikiriki

insert into users_user_roles(user_entity_id, user_roles_id)
values (1,1),
       (1,2),
       (2,2);

insert into brands (id, name)
values (1, 'Ford'), (2, 'Toyota'), (3, 'Renault'), (4, 'BMW');

insert into models (start_year, brand_id, end_year, id, category, image_url, name)
values (2020, 1, 2024, 1, 'CAR', 'https://dam.which.co.uk/WH12990-0257-00-front-2000x1500.jpg', 'Kuga'),
        (1978, 2, 2019, 2, 'CAR', 'https://www.buyatoyota.com/assets/img/vehicle-info/Supra/2024/hero-image.png', 'Supra'),
        (2015, 3, 2022, 3, 'CAR','https://cdn3.focus.bg/autodata/i/renault/talisman/talisman-combi/large/5226f2259b206b2ad9e1b36a4b9580ad.jpg', 'Talisman'),
        (2014, 4, 2020, 4, 'CAR','https://mobistatic2.focus.bg/mobile/photosorg/866/1/big//11693903733992866_8o.jpg', 'i8');

insert into offers (mileage, price, year, id, model_id, seller_id, description, engine, image_url, transmission)
values (2000, 35000,2020, 1, 1, 1,'Кукла :*', 'HYBRID','https://dam.which.co.uk/WH12990-0257-00-front-2000x1500.jpg','AUTOMATIC'),
        (3000, 45000,2021, 2, 2, 1,'Разкош :Р', 'DIESEL','https://www.buyatoyota.com/assets/img/vehicle-info/Supra/2024/hero-image.png','AUTOMATIC'),
        (4000, 25000,2022, 3, 3, 2,'Жал ме е да я продавам! :)', 'GASOLINE','https://cdn3.focus.bg/autodata/i/renault/talisman/talisman-combi/large/5226f2259b206b2ad9e1b36a4b9580ad.jpg','AUTOMATIC'),
        (5000, 30000,2023, 4, 4, 2,'Топ', 'ELECTRIC','https://mobistatic2.focus.bg/mobile/photosorg/866/1/big//11693903733992866_8o.jpg','AUTOMATIC');
