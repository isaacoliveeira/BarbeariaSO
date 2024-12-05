import java.util.concurrent.Semaphore;

public class Barbearia  {

    private int max = 4;
    private int begin = 0, end = 0;
    private int[] fila = new int[max+1]; 

    private Semaphore mutex = new Semaphore(1); //exclusao mutua
    private Semaphore cliente = new Semaphore(0);
    private Semaphore barbeiro = new Semaphore(0);

    public void enter(int clienteId) throws InterruptedException {
        mutex.acquire(); //o babreiro so pode atender um
        if((end + 1) % fila.length == begin) {
            System.out.println("Cliente " + clienteId + " foi embora sme espaço na sala");
        } else {
            fila[end] = clienteId;
            end = (end + 1) % fila.length;
            System.out.println("Cliente " + " sentou na sala de espera");
            this.cliente.release(); // existe cliente disponivel;
        }
        mutex.release();
    }

    public void meet() throws InterruptedException {
        cliente.acquire(); //esperando cliente "dormindo"
        mutex.acquire();
        int clienteId = fila[begin];
        begin = (begin + 1) % fila.length;
        System.out.println("O barbeiro está atendendo o cliente " + clienteId);
        mutex.release();
        this.barbeiro.release(); //barbeiro ocupado
        Thread.sleep(1000); //tempo de atendimento
    }

    public void out(int clienteId) throws InterruptedException {
        barbeiro.acquire();
        System.out.println("O cliente" + clienteId + " foi atendido e saiu");
    }
}