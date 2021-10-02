package controladores;

public class ControladorClase <T> {

    IdentificadorDeClase claseId;

    public ControladorClase(T t) {
        this.claseId= new IdentificadorDeClase(t);
    }

    public boolean add (T t){



        return true;
    }

}
