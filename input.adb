 procedure hello 
(
    paramOne : Integer := 10; 
    paramTwo : Integer := 10;
    paramThree : Integer
) is
    paramFive : Integer := 4;
begin

    paramOne := paramOne * 2;
    paramFour := paramOne + paramTwo;
    paramFive := (paramOne + 5) * paramThree;

end hello;


procedure singleArgs (paramOne : Float)
is
begin
end singleArgs;

procedure noArgs is 
begin

end noArgs;

procedure input is
begin
    hello(1, 4, 3);

end input;