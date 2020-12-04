procedure test (param: Integer) is
    local : Integer := 4;
begin

    local := local + (param * 10);

end test;

procedure input is
begin
    test(3);
end input;