package App;

public class PharmFieldsException extends Exception{
    public PharmFieldsException()
    {
        super("Ошибка добавления/изменений, введите все поля корректно");
    }
}