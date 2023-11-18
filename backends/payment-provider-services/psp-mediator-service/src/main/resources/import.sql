insert into public.subscription_service(id, queue_name, name)
values ('0cf43270-3766-42c8-8fe8-74e416a68f41', 'card-service-queue', 'Card')
on conflict(id) do nothing;

insert into public.subscription_service(id, queue_name, name)
values ('252259dd-08a5-4eba-b2e6-7bfbddda35e9', 'qrcode-service-queue', 'QrCode')
on conflict(id) do nothing;

insert into public.subscription_service(id, queue_name, name)
values ('b8dc6eea-7e99-444f-b355-f99e0fc936eb', 'paypal-service-queue', 'PayPal')
on conflict(id) do nothing;

insert into public.subscription_service(id, queue_name, name)
values ('df632760-7c54-451e-9428-dcf739ac3c3e', 'crypto-service-queue', 'Crypto')
on conflict(id) do nothing;