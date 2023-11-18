create or replace function generate_uuid()
    returns uuid
as
    $$
    begin
        return gen_random_uuid();
    end;
    $$
LANGUAGE plpgsql;

select * from generate_uuid();