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