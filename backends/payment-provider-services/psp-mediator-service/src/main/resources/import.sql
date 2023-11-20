insert into public.subscription_services(id, queue_name, name, image_url)
values ('0cf43270-3766-42c8-8fe8-74e416a68f41', 'card-service-queue', 'Card','https://wedoblob.blob.core.windows.net/psp/card.png')
on conflict(id) do nothing;

insert into public.subscription_services(id, queue_name, name,image_url)
values ('252259dd-08a5-4eba-b2e6-7bfbddda35e9', 'qrcode-service-queue', 'QrCode','https://wedoblob.blob.core.windows.net/psp/qr.png')
on conflict(id) do nothing;

insert into public.subscription_services(id, queue_name, name, image_url)
values ('b8dc6eea-7e99-444f-b355-f99e0fc936eb', 'paypal-service-queue', 'PayPal','https://wedoblob.blob.core.windows.net/psp/paypal.png')
on conflict(id) do nothing;

insert into public.subscription_services(id, queue_name, name, image_url)
values ('df632760-7c54-451e-9428-dcf739ac3c3e', 'crypto-service-queue', 'Crypto', 'https://wedoblob.blob.core.windows.net/psp/crypto-bitcoin.png')
on conflict(id) do nothing;

INSERT INTO public.merchants (id, email, password, roles, bank_account, name) VALUES ('ff7307fd-e64d-46cb-8539-50f9ba14f66b', 'lawify@gmail.com', '$2a$10$iUYcPHUZXafgTLXZACjXxe3bz5tMexuqoT7bzbo7KS7mldCbvuTQO', '{0}', E'\\xDEEAD6AB79353A3FD1DFA50D042703D59A4F2262722A6861112E6C6AF82F8F4D', 'Lawify');
INSERT INTO public.api_keys (id, created_at, expired_at, key, merchant_id) VALUES ('341b2916-e4f5-41d5-a4a1-2c74eba4a361', '2023-11-20 19:45:03.757000', '2024-11-20 19:44:39.808000', '5977e7ade6a9f43a2386ef4d53f23fb04653f0b021c83eecf4ed25ac2b2cb172', 'ff7307fd-e64d-46cb-8539-50f9ba14f66b');