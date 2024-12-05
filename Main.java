public class Main {
    public static void main(String[] args) {
        Barbearia barbearia = new Barbearia();

        // Thread do barbeiro
        Thread barbeiro = new Thread(() -> {
            while (true) {
                try {
                    barbearia.meet();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        // Threads dos clientes
        for (int i = 1; i <= 10; i++) {
            final int clienteId = i;
            new Thread(() -> {
                try {
                    barbearia.enter(clienteId);
                } catch (InterruptedException e) {

                }
                try {
                    barbearia.out(clienteId);
                } catch (InterruptedException e) {
                    
                }
            }).start();
            
            try {
                Thread.sleep(2000); // Simula o intervalo de chegada dos clientes
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Inicia o barbeiro
        barbeiro.start();
    }
}
