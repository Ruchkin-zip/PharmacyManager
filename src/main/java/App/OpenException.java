package App;

public class OpenException extends Exception{
    public OpenException()
    {
        super("Сначала необходимо открыть БД аптеки");
    }
}
