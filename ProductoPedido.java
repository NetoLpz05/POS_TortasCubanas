public class ProductoPedido {

    private int idProductoPedido;
    private double precio;
    private String detalles;
    private int pedidoIdPedido;
    private int carritoIdCarrito;
    private int productoIdProducto;

    public ProductoPedido() {
    }

    public ProductoPedido(int idProductoPedido, double precio, String detalles, int pedidoIdPedido, int carritoIdCarrito, int productoIdProducto) {
        this.idProductoPedido = idProductoPedido;
        this.precio = precio;
        this.detalles = detalles;
        this.pedidoIdPedido = pedidoIdPedido;
        this.carritoIdCarrito = carritoIdCarrito;
        this.productoIdProducto = productoIdProducto;
    }

    public int getIdProductoPedido() {
        return idProductoPedido;
    }

    public void setIdProductoPedido(int idProductoPedido) {
        this.idProductoPedido = idProductoPedido;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public int getPedidoIdPedido() {
        return pedidoIdPedido;
    }

    public void setPedidoIdPedido(int pedidoIdPedido) {
        this.pedidoIdPedido = pedidoIdPedido;
    }

    public int getCarritoIdCarrito() {
        return carritoIdCarrito;
    }

    public void setCarritoIdCarrito(int carritoIdCarrito) {
        this.carritoIdCarrito = carritoIdCarrito;
    }

    public int getProductoIdProducto() {
        return productoIdProducto;
    }

    public void setProductoIdProducto(int productoIdProducto) {
        this.productoIdProducto = productoIdProducto;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "ProductoPedido{" +
                "idProductoPedido=" + idProductoPedido +
                ", precio=" + precio +
                ", detalles='" + detalles + '\'' +
                ", pedidoIdPedido=" + pedidoIdPedido +
                ", carritoIdCarrito=" + carritoIdCarrito +
                ", productoIdProducto=" + productoIdProducto +
                '}';
    }
}