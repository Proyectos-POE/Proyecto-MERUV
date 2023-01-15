package controlador;

import modelo.*;
import vista.VentanaMercado;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.lang.Math;
public class ControladorMercado
{
    private final Empresa superMercadoUV;
    private final VentanaMercado ventanaMercado;

    public ControladorMercado(Empresa auxEmpresa, VentanaMercado auxA)
    {
        this.superMercadoUV = auxEmpresa;
        this.ventanaMercado = auxA;
        ventanaMercado.setVisible(true);
        ventanaMercado.setLocationRelativeTo(null);
        TextFieldListener textFieldListener = new TextFieldListener();

        //----------|Cliente|----------//
        ClienteListener clienteListener = new ClienteListener();
        this.ventanaMercado.addBtnAgregarClienteListener(clienteListener);
        this.ventanaMercado.addBtnEditarClienteListener(clienteListener);
        this.ventanaMercado.addBtnEliminarClienteListener(clienteListener);

        //----------|Proveedor|----------//
        ProveedorListener proveedorListener = new ProveedorListener();
        this.ventanaMercado.addBtnAgregarProveedorListener(proveedorListener);
        this.ventanaMercado.addBtnEditarProveedorListener(proveedorListener);
        this.ventanaMercado.addBtnEliminarProveedorListener(proveedorListener);

        //----------|Compra|----------//
        CompraListener compraListener = new CompraListener();
        this.ventanaMercado.addBtnRealizarCompraListener(compraListener);
        this.ventanaMercado.addBtnAgregarCompraListener(compraListener);
        this.ventanaMercado.addBtnEliminarCompraListener(compraListener);
        this.ventanaMercado.addTextFieldComprarListener(textFieldListener);

        //----------|Producto|----------//
        ProductoListener productoListener = new ProductoListener();
        this.ventanaMercado.addBtnAgregarProductoListener(productoListener);
        this.ventanaMercado.addBtnEditarProductoListener(productoListener);
        this.ventanaMercado.addBtnEliminarProductoListener(productoListener);

    }

    //----------|Clientes|----------//
    private boolean comprobarCamposCliente()
    {
        boolean camposValido;
        camposValido = true;
        if(ventanaMercado.getNombreCliente().equals("") || ventanaMercado.getNitCliente().equals("") || ventanaMercado.getTelefonoCliente().equals("") || ventanaMercado.getDireccionCliente().equals(""))
        {
            camposValido = false;
        }
        return camposValido;
    }

    private boolean comprobarNITCliente(int auxId, long auxNIT)
    {
        boolean nitValido;
        nitValido = true;
        ArrayList<Cliente> auxClientes;
        auxClientes = superMercadoUV.getClientes();

        for(Cliente cliente : auxClientes)
        {
            if(cliente.getNIT() == auxNIT && cliente.getId() != auxId)
            {
                nitValido = false;
            }
        }
        return nitValido;
    }

    private void listarClientesAgregar(Cliente auxCliente)
    {
        int auxId = auxCliente.getId();
        long auxNit = auxCliente.getNIT();
        String auxNombre = auxCliente.getNombre();
        long auxTelefono = auxCliente.getTelefono();
        String auxDireccion = auxCliente.getDireccion();

        DefaultTableModel auxModeloTabla = (DefaultTableModel) ventanaMercado.getModelTablaCliente();
        auxModeloTabla.addRow(new Object[]{auxId, auxNit, auxNombre, auxTelefono, auxDireccion});
    }

    private void listarClientesEditar(Cliente auxCliente)
    {
        int auxId = auxCliente.getId();
        long auxNit = auxCliente.getNIT();
        String auxNombre = auxCliente.getNombre();
        long auxTelefono = auxCliente.getTelefono();
        String auxDireccion = auxCliente.getDireccion();

        DefaultTableModel auxModeloTabla = (DefaultTableModel) ventanaMercado.getModelTablaCliente();
        int auxFila = ventanaMercado.getFilaSeleccionadaCliente();

        auxModeloTabla.setValueAt(auxId, auxFila, 0);
        auxModeloTabla.setValueAt(auxNit, auxFila, 1);
        auxModeloTabla.setValueAt(auxNombre, auxFila, 2);
        auxModeloTabla.setValueAt(auxTelefono, auxFila, 3);
        auxModeloTabla.setValueAt(auxDireccion, auxFila, 4);
    }

    private void listarClientesEliminar()
    {
        DefaultTableModel auxModeloTabla = (DefaultTableModel) ventanaMercado.getModelTablaCliente();
        int auxFila = ventanaMercado.getFilaSeleccionadaCliente();
        auxModeloTabla.removeRow(auxFila);
    }

    private void agregarCliente()
    {
        Cliente auxCliente;
        int auxId;
        long auxNIT;
        String auxNombre;
        long auxTelefono;
        String auxDireccion;

        auxId = Integer.parseInt(ventanaMercado.getIdCliente());
        if(auxId == 0)
        {
            if(comprobarCamposCliente())
            {
                try
                {
                    auxNIT = Long.parseLong(ventanaMercado.getNitCliente());
                    auxNombre = ventanaMercado.getNombreCliente();
                    auxTelefono = Long.parseLong(ventanaMercado.getTelefonoCliente());
                    auxDireccion = ventanaMercado.getDireccionCliente();

                    if(comprobarNITCliente(0 ,auxNIT))
                    {
                        auxCliente = new Cliente(auxNIT, auxNombre, auxTelefono, auxDireccion);
                        if(superMercadoUV.agregarCliente(auxCliente))
                        {
                            ventanaMercado.mostrarMensaje("Cliente agregado con exito");
                            ventanaMercado.setIdCliente("0");
                            ventanaMercado.setNitCliente("");
                            ventanaMercado.setNombreCliente("");
                            ventanaMercado.setTelefonoCliente("");
                            ventanaMercado.setDireccionCliente("");
                            listarClientesAgregar(auxCliente);
                        }
                        else
                        {
                            ventanaMercado.mostrarMensajeError("Cliente agregado sin exito");
                        }
                    }
                    else
                    {
                        ventanaMercado.mostrarMensajeError("NIT ya registrado");
                    }
                }
                catch (NumberFormatException e)
                {
                    ventanaMercado.mostrarMensajeError("Ingrese numeros enteros en los campos NIT y Telefono");
                }
            }
            else
            {
                ventanaMercado.mostrarMensajeError("Rellene todos los campos");
            }
        }
        else
        {
            ventanaMercado.mostrarMensajeError("Deseleccione el cliente");
        }

    }

    private void editarCliente()
    {
        Cliente auxCliente;
        int auxId;
        long auxNIT;
        String auxNombre;
        long auxTelefono;
        String auxDireccion;

        auxId = Integer.parseInt(ventanaMercado.getIdCliente());
        auxCliente = superMercadoUV.getCliente(auxId);

        if(auxCliente != null)
        {
            if(comprobarCamposCliente())
            {
                try
                {
                    auxNIT = Long.parseLong(ventanaMercado.getNitCliente());
                    auxNombre = ventanaMercado.getNombreCliente();
                    auxTelefono = Long.parseLong(ventanaMercado.getTelefonoCliente());
                    auxDireccion = ventanaMercado.getDireccionCliente();

                    if(comprobarNITCliente(auxId, auxNIT))
                    {
                        auxCliente.setNIT(auxNIT);
                        auxCliente.setNombre(auxNombre);
                        auxCliente.setTelefono(auxTelefono);
                        auxCliente.setDireccion(auxDireccion);

                        if(superMercadoUV.actualizarCliente(auxCliente))
                        {
                            ventanaMercado.mostrarMensaje("Cliente editado con exito");
                            ventanaMercado.setIdCliente("0");
                            ventanaMercado.setNitCliente("");
                            ventanaMercado.setNombreCliente("");
                            ventanaMercado.setTelefonoCliente("");
                            ventanaMercado.setDireccionCliente("");
                            listarClientesEditar(auxCliente);
                            ventanaMercado.deseleccionarFilaTablaCliente();
                        }
                        else
                        {
                            ventanaMercado.mostrarMensajeError("Cliente editado sin exito");
                        }
                    }
                    else
                    {
                        ventanaMercado.mostrarMensajeError("NIT ya registrado");
                    }
                }
                catch (NumberFormatException e)
                {
                    ventanaMercado.mostrarMensajeError("Ingrese numeros enteros en los campos NIT y Telefono");
                }
            }
            else
            {
                ventanaMercado.mostrarMensajeError("Rellene todos los campos");
            }
        }
        else
        {
            ventanaMercado.mostrarMensajeError("Seleccione a un cliente");
        }
    }

    private void eliminarCliente()
    {
        Cliente auxCliente;
        int auxId;
        auxId = Integer.parseInt(ventanaMercado.getIdCliente());
        auxCliente = superMercadoUV.getCliente(auxId);

        if(auxCliente != null)
        {
            if(superMercadoUV.eliminarCliente(auxCliente))
            {
                ventanaMercado.mostrarMensaje("Cliente eliminado con exito");
                ventanaMercado.setIdCliente("0");
                ventanaMercado.setNitCliente("");
                ventanaMercado.setNombreCliente("");
                ventanaMercado.setTelefonoCliente("");
                ventanaMercado.setDireccionCliente("");
                listarClientesEliminar();
                ventanaMercado.deseleccionarFilaTablaCliente();
            }
            else
            {
                ventanaMercado.mostrarMensajeError("Cliente eliminado sin exito");
            }
        }
        else
        {
            ventanaMercado.mostrarMensajeError("Seleccione a un cliente");
        }
    }

    class ClienteListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (e.getActionCommand().equalsIgnoreCase("AGREGAR"))
            {
                agregarCliente();
            }
            if (e.getActionCommand().equalsIgnoreCase("EDITAR"))
            {
                editarCliente();
            }
            if (e.getActionCommand().equalsIgnoreCase("ELIMINAR"))
            {
                eliminarCliente();
            }
        }
    }

    //----------|Proveedor|----------//

    class ProveedorListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (e.getActionCommand().equalsIgnoreCase("AGREGAR"))
            {
                agregarProveedor();
            }
            if (e.getActionCommand().equalsIgnoreCase("EDITAR"))
            {
                editarProveedor();
            }
            if (e.getActionCommand().equalsIgnoreCase("ELIMINAR"))
            {
                eliminarProveedor();
            }
        }
    }
    private void agregarProveedor()
    {
        Proveedor auxProveedor;
        int auxId;
        long auxNIT;
        String auxNombre;
        long auxTelefono;
        String auxDireccion;
        String auxRazonSocial;

        auxId = Integer.parseInt(ventanaMercado.getIdCliente());
        if(auxId == 0)
        {
            if(comprobarCamposProveedor())
            {
                try
                {
                    auxNIT = Long.parseLong(ventanaMercado.getNitProveedor());
                    auxNombre = ventanaMercado.getNombreProveedor();
                    auxTelefono = Long.parseLong(ventanaMercado.getTelefonoProveedor());
                    auxDireccion = ventanaMercado.getDireccionProveedor();
                    auxRazonSocial = ventanaMercado.getRazonSocialProveedor();

                    if(comprobarNitProveedor(0 ,auxNIT))
                    {
                        auxProveedor = new Proveedor(auxNombre, auxNIT, auxTelefono, auxDireccion, auxRazonSocial);
                        if(superMercadoUV.agregarProveedor(auxProveedor))
                        {
                            ventanaMercado.mostrarMensaje("Proveedor agregado con exito");
                            ventanaMercado.setIdProveedor("0");
                            ventanaMercado.setNitProveedor("");
                            ventanaMercado.setNombreProveedor("");
                            ventanaMercado.setTelefonoProveedor("");
                            ventanaMercado.setDireccionProveedor("");
                            ventanaMercado.setRazonSocialProveedor("");
                            listarProveedoresAgregar(auxProveedor);
                            ventanaMercado.rellenarProveedorComprar(auxProveedor);
                        }
                        else
                        {
                            ventanaMercado.mostrarMensajeError("Proveedor no agregado");
                        }
                    }
                    else
                    {
                        ventanaMercado.mostrarMensajeError("NIT ya registrado");
                    }
                }
                catch (NumberFormatException e)
                {
                    ventanaMercado.mostrarMensajeError("Ingrese numeros enteros en los campos NIT y Telefono");
                }
            }
            else
            {
                ventanaMercado.mostrarMensajeError("Rellene todos los campos");
            }
        }
        else
        {
            ventanaMercado.mostrarMensajeError("Deseleccione el proveedor");
        }

    }

    private boolean comprobarCamposProveedor()
    {
        boolean camposValido;
        camposValido = true;
        if(ventanaMercado.getNombreProveedor().equals("") || ventanaMercado.getNitProveedor().equals("") || ventanaMercado.getTelefonoProveedor().equals("") || ventanaMercado.getDireccionProveedor().equals("") || ventanaMercado.getRazonSocialProveedor().equals(""))
        {
            camposValido = false;
        }
        return camposValido;
    }

    private boolean comprobarNitProveedor(int auxId, Long auxNit)
    {
        boolean nitValido;
        nitValido = true;
        ArrayList<Proveedor> auxProveedores;
        auxProveedores = superMercadoUV.getProveedores();
        for(Proveedor proveedor : auxProveedores)
        {
            if(proveedor.getNit() == auxNit && proveedor.getId() != auxId)
            {
                nitValido = false;
            }
        }
        return nitValido;
    }

    private void editarProveedor()
    {
        Proveedor auxProveedor;
        int auxId;
        long auxNIT;
        String auxNombre;
        long auxTelefono;
        String auxDireccion;
        String auxRazonSocial;

        auxId = Integer.parseInt(ventanaMercado.getIdProveedor());
        auxProveedor = superMercadoUV.getProveedor(auxId);

        if(auxProveedor != null)
        {
            if(comprobarCamposProveedor())
            {
                try
                {
                    auxNIT = Long.parseLong(ventanaMercado.getNitProveedor());
                    auxNombre = ventanaMercado.getNombreProveedor();
                    auxTelefono = Long.parseLong(ventanaMercado.getTelefonoProveedor());
                    auxDireccion = ventanaMercado.getDireccionProveedor();
                    auxRazonSocial = ventanaMercado.getRazonSocialProveedor();

                    if(comprobarNitProveedor(auxId ,auxNIT))
                    {
                        if(superMercadoUV.actualizarProveedor(auxProveedor))
                        {
                            ventanaMercado.mostrarMensaje("Proveedor editado con exito");
                            ventanaMercado.setIdProveedor("0");
                            ventanaMercado.setNitProveedor("");
                            ventanaMercado.setNombreProveedor("");
                            ventanaMercado.setTelefonoProveedor("");
                            ventanaMercado.setDireccionProveedor("");
                            ventanaMercado.setRazonSocialProveedor("");
                            listarProveedoresEditar(auxProveedor);
                            ventanaMercado.deseleccionarFilaTablaProveedor();
                        }
                        else
                        {
                            ventanaMercado.mostrarMensajeError("Proveedor no editado");
                        }
                    }
                    else
                    {
                        ventanaMercado.mostrarMensajeError("NIT ya registrado");
                    }
                }
                catch (NumberFormatException e)
                {
                    ventanaMercado.mostrarMensajeError("Ingrese numeros enteros en los campos NIT y Telefono");
                }
            }
            else
            {
                ventanaMercado.mostrarMensajeError("Rellene todos los campos");
            }
        }
        else
        {
            ventanaMercado.mostrarMensajeError("Seleccione un proveedor");
        }
    }

    private void eliminarProveedor()
    {
        Proveedor auxProveedor;
        int auxId;
        auxId = Integer.parseInt(ventanaMercado.getIdProveedor());
        auxProveedor = superMercadoUV.getProveedor(auxId);

        if(auxProveedor != null)
        {
            if(superMercadoUV.eliminarProveedor(auxProveedor))
            {
                ventanaMercado.mostrarMensaje("Proveedor eliminado con exito");
                ventanaMercado.setIdProveedor("0");
                ventanaMercado.setNitProveedor("");
                ventanaMercado.setNombreProveedor("");
                ventanaMercado.setTelefonoProveedor("");
                ventanaMercado.setDireccionProveedor("");
                ventanaMercado.setRazonSocialProveedor("");
                listarProveedoresEliminar();
                ventanaMercado.deseleccionarFilaTablaProveedor();
            }
            else
            {
                ventanaMercado.mostrarMensajeError("Proveedor no eliminado");
            }
        }
        else
        {
            ventanaMercado.mostrarMensajeError("Seleccione a un proveedor");
        }
    }

    private void listarProveedoresAgregar(Proveedor auxProveedor)
    {
        int auxId = auxProveedor.getId();
        long auxNit = auxProveedor.getNit();
        String auxNombre = auxProveedor.getNombre();
        long auxTelefono = auxProveedor.getTelefono();
        String auxDireccion = auxProveedor.getDireccion();
        String auxRazonSocial = auxProveedor.getRazonSocial();

        DefaultTableModel auxModeloTabla = (DefaultTableModel) ventanaMercado.getModelTablaProveedor();
        auxModeloTabla.addRow(new Object[]{auxId, auxNit, auxNombre, auxTelefono, auxDireccion, auxRazonSocial});
    }

    private void listarProveedoresEditar(Proveedor auxProveedor)
    {
        int auxId = auxProveedor.getId();
        long auxNit = auxProveedor.getNit();
        String auxNombre = auxProveedor.getNombre();
        long auxTelefono = auxProveedor.getTelefono();
        String auxDireccion = auxProveedor.getDireccion();
        String auxRazonSocial = auxProveedor.getRazonSocial();

        DefaultTableModel auxModeloTabla = (DefaultTableModel) ventanaMercado.getModelTablaProveedor();
        int auxFila = ventanaMercado.getFilaSeleccionadaProveedor();

        auxModeloTabla.setValueAt(auxId, auxFila, 0);
        auxModeloTabla.setValueAt(auxNit, auxFila, 1);
        auxModeloTabla.setValueAt(auxNombre, auxFila, 2);
        auxModeloTabla.setValueAt(auxTelefono, auxFila, 3);
        auxModeloTabla.setValueAt(auxDireccion, auxFila, 4);
        auxModeloTabla.setValueAt(auxRazonSocial, auxFila, 5);
    }

    private void listarProveedoresEliminar()
    {
        DefaultTableModel auxModeloTabla = (DefaultTableModel) ventanaMercado.getModelTablaProveedor();
        int auxFila = ventanaMercado.getFilaSeleccionadaProveedor();
        auxModeloTabla.removeRow(auxFila);
    }

    //----------|Producto|----------//

    class ProductoListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (e.getActionCommand().equalsIgnoreCase("AGREGAR"))
            {
                agregarProducto();
            }
            if (e.getActionCommand().equalsIgnoreCase("EDITAR"))
            {
                editarProducto();
            }
            if (e.getActionCommand().equalsIgnoreCase("ELIMINAR"))
            {
                eliminarProducto();
            }
        }
    }

    private boolean comprobarCamposProducto()
    {
        boolean camposValido;
        camposValido = true;
        if(ventanaMercado.getNombreProducto().equals("") || ventanaMercado.getCodigoProducto().equals("") || ventanaMercado.getPrecioVentaProducto().equals("") || ventanaMercado.getCategoriaProducto()==null)
        {
            camposValido = false;
        }
        return camposValido;
    }

    private boolean comprobarCodigoProducto(int auxId, long auxCodigo)
    {
        boolean codigoValido;
        codigoValido = true;
        ArrayList<Producto> auxProductos;
        auxProductos = superMercadoUV.getProductos();
        for(Producto producto : auxProductos)
        {
            if(producto.getCodigo() == auxCodigo && producto.getId() != auxId)
            {
                codigoValido = false;
            }
        }
        return codigoValido;
    }

    private void agregarProducto()
    {
        Producto auxProducto;
        int auxId;
        long auxCodigo;
        String auxNombre;
        double auxPrecioVenta;
        String auxCategoria;

        auxId = Integer.parseInt(ventanaMercado.getIdProducto());
        if(auxId == 0)
        {
            if(comprobarCamposProducto())
            {
                try
                {
                    auxCodigo = Long.parseLong(ventanaMercado.getCodigoProducto());
                    auxNombre = ventanaMercado.getNombreProducto();
                    auxPrecioVenta = Double.parseDouble(ventanaMercado.getPrecioVentaProducto());
                    auxCategoria = ventanaMercado.getCategoriaProducto();

                    if(comprobarCodigoProducto(0 ,auxCodigo))
                    {
                        auxProducto = new Producto(auxCodigo, auxNombre, 0,auxPrecioVenta,0, auxCategoria);
                        if(superMercadoUV.agregarProducto(auxProducto))
                        {
                            ventanaMercado.mostrarMensaje("Producto agregado con exito");
                            ventanaMercado.setIdProducto("0");
                            ventanaMercado.setCodigoProducto("");
                            ventanaMercado.setNombreProducto("");
                            ventanaMercado.setPrecioVentaProducto("");
                            ventanaMercado.setStockProducto("");
                            ventanaMercado.setCategoriaProducto(null);
                            listarProductosAgregar(auxProducto);
                        }
                        else
                        {
                            ventanaMercado.mostrarMensajeError("Producto no agregado");
                        }
                    }
                    else
                    {
                        ventanaMercado.mostrarMensajeError("Codigo ya registrado");
                    }
                }
                catch (NumberFormatException e)
                {
                    ventanaMercado.mostrarMensajeError("Ingrese numeros enteros en el campo Codigo y Precio Venta");
                }
            }
            else
            {
                ventanaMercado.mostrarMensajeError("Rellene todos los campos");
            }
        }
        else
        {
            ventanaMercado.mostrarMensajeError("Deseleccione el producto");
        }
    }

    private void editarProducto()
    {
        Producto auxProducto;
        int auxId;
        long auxCodigo;
        String auxNombre;
        double auxPrecioVenta;
        String auxCategoria;

        auxId = Integer.parseInt(ventanaMercado.getIdProducto());
        auxProducto = superMercadoUV.getProducto(auxId);

        if(auxProducto != null)
        {
            if(comprobarCamposProducto())
            {
                try
                {
                    auxNombre = ventanaMercado.getNombreProducto();
                    auxPrecioVenta = Double.parseDouble(ventanaMercado.getPrecioVentaProducto());

                    auxProducto.setNombre(auxNombre);
                    auxProducto.setPrecioVenta(auxPrecioVenta);

                    if(superMercadoUV.actualizarProducto(auxProducto))
                    {
                        ventanaMercado.mostrarMensaje("Producto editado con exito");
                        ventanaMercado.setIdProducto("0");
                        ventanaMercado.setCodigoProducto("");
                        ventanaMercado.setNombreProducto("");
                        ventanaMercado.setPrecioVentaProducto("");
                        ventanaMercado.setStockProducto("");
                        ventanaMercado.setCategoriaProducto(null);
                        ventanaMercado.activarCategoriaProducto();
                        ventanaMercado.activarCodigoProducto();
                        listarProductosEditar(auxProducto);
                        ventanaMercado.deseleccionarFilaTablaInventario();
                    }
                    else
                    {
                        ventanaMercado.mostrarMensajeError("Producto no editado");
                    }
                }
                catch (NumberFormatException e)
                {
                    ventanaMercado.mostrarMensajeError("Ingrese numeros enteros en el campo Codigo y Precio Venta");
                }
            }
            else
            {
                ventanaMercado.mostrarMensajeError("Rellene todos los campos");
            }
        }
        else
        {
            ventanaMercado.mostrarMensajeError("Seleccione un producto");
        }

    }

    private void eliminarProducto()
    {
        Producto auxProducto;
        int auxId;
        auxId = Integer.parseInt(ventanaMercado.getIdProducto());
        auxProducto = superMercadoUV.getProducto(auxId);

        if(auxProducto != null)
        {
            if(superMercadoUV.eliminarProducto(auxProducto))
            {
                ventanaMercado.mostrarMensaje("Producto eliminado con exito");
                ventanaMercado.setIdProducto("0");
                ventanaMercado.setCodigoProducto("");
                ventanaMercado.setNombreProducto("");
                ventanaMercado.setPrecioVentaProducto("");
                ventanaMercado.setStockProducto("");
                ventanaMercado.setCategoriaProducto(null);
                ventanaMercado.activarCategoriaProducto();
                ventanaMercado.activarCodigoProducto();
                listarProductosEliminar();
                ventanaMercado.deseleccionarFilaTablaInventario();
            }
            else
            {
                ventanaMercado.mostrarMensajeError("Producto no eliminado");
            }
        }
        else
        {
            ventanaMercado.mostrarMensajeError("Seleccione un producto");
        }
    }

    private void listarProductosAgregar(Producto auxProducto)
    {
        int auxId = auxProducto.getId();
        long auxCodigo = auxProducto.getCodigo();
        String auxNombre = auxProducto.getNombre();
        double auxPrecioVenta = auxProducto.getPrecioVenta();
        int auxStock = auxProducto.getStock();
        String auxCategoria = auxProducto.getCategoria();

        DefaultTableModel auxModeloTabla = (DefaultTableModel) ventanaMercado.getModelTablaProducto();
        auxModeloTabla.addRow(new Object[]{auxId, auxCodigo, auxNombre, auxPrecioVenta, auxStock, auxCategoria});
    }

    private void listarProductosEditar(Producto auxProducto)
    {
        int auxId = auxProducto.getId();
        long auxCodigo = auxProducto.getCodigo();
        String auxNombre = auxProducto.getNombre();
        double auxPrecioVenta = auxProducto.getPrecioVenta();
        int auxStock = auxProducto.getStock();
        String auxCategoria = auxProducto.getCategoria();

        DefaultTableModel auxModeloTabla = (DefaultTableModel) ventanaMercado.getModelTablaProducto();
        int auxFila = ventanaMercado.getFilaSeleccionadaProducto();

        auxModeloTabla.setValueAt(auxId, auxFila, 0);
        auxModeloTabla.setValueAt(auxCodigo, auxFila, 1);
        auxModeloTabla.setValueAt(auxNombre, auxFila, 2);
        auxModeloTabla.setValueAt(auxPrecioVenta, auxFila, 3);
        auxModeloTabla.setValueAt(auxStock, auxFila, 4);
        auxModeloTabla.setValueAt(auxCategoria, auxFila, 5);
    }

    private void listarProductosEliminar()
    {
        DefaultTableModel auxModeloTabla = (DefaultTableModel) ventanaMercado.getModelTablaProducto();
        int auxFila = ventanaMercado.getFilaSeleccionadaProducto();
        auxModeloTabla.removeRow(auxFila);
    }

    //----------|Compra|----------//

    class CompraListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
             if(e.getActionCommand().equalsIgnoreCase("agregar"))
             {
                 agregarProductoCompra();
             }
             if(e.getActionCommand().equalsIgnoreCase("COMPRAR"))
             {
                 realizarCompra();
             }
             if(e.getActionCommand().equalsIgnoreCase("eliminar"))
             {
                 eliminarProductoCarritoCompra();
             }
        }
    }

    private void agregarProductoCompra()
    {
        if (ventanaMercado.getFilaSeleccionadaComprar() == -1)
        {
            Producto auxProducto;
            long auxCodigo;
            String auxNombre;
            double auxPrecioCompra;
            int auxStock;

            if (comprobarCamposCarritoCompra())
            {
                try
                {
                    auxCodigo = Long.parseLong(ventanaMercado.getCodigoComprar());
                    auxNombre = ventanaMercado.getNombreComprar();
                    auxPrecioCompra = Double.parseDouble(ventanaMercado.getPrecioCompraComprar());
                    auxStock = Integer.parseInt(ventanaMercado.getStockComprar());

                    if(auxStock>0)
                    {

                        if (comprobarExistenciaProducto(auxCodigo))
                        {
                            auxProducto = new Producto(auxCodigo, auxNombre, auxPrecioCompra, auxStock);
                            if (superMercadoUV.agregarProductoCarritoCompra(auxProducto))
                            {
                                listarCarritoCompraAgregar(auxProducto);
                                superMercadoUV.calcularPrecioTotalCompra();
                                superMercadoUV.calcularProductosTotalesCompra();

                                ventanaMercado.setCodigoComprar("");
                                ventanaMercado.setNombreComprar("");
                                ventanaMercado.setPrecioCompraComprar("");
                                ventanaMercado.setStockComprar("");
                                ventanaMercado.setPrecioTotalComprar("" + Math.round(superMercadoUV.getPrecioTotalCompra()));
                                ventanaMercado.setProductosTotalesComprar("" + superMercadoUV.getProductosTotalesCompra());
                            }
                        }
                        else
                        {
                            ventanaMercado.mostrarMensajeError("El producto no existe, por favor agreguelo al inventario antes de continuar");
                        }
                    }
                }
                catch (NumberFormatException e)
                {
                    ventanaMercado.mostrarMensajeError("Ingrese valores validos en los campos codigo, stock y precio compra");
                }
            }
            else
            {
                ventanaMercado.mostrarMensajeError("Rellene los campos correctamente");
            }
        }
    }

    private Boolean comprobarCamposCarritoCompra()
    {
        boolean camposValidos = true;

        if(ventanaMercado.getCodigoComprar().equals("") || ventanaMercado.getPrecioCompraComprar().equals("") || ventanaMercado.getStockComprar().equals(""))
        {
            camposValidos = false;
        }

        return camposValidos;
    }

    private Boolean comprobarExistenciaProducto(long auxCodigo)
    {
        boolean existe = false;
        ArrayList<Producto> productos;
        productos = superMercadoUV.getProductos();
        if(productos!=null)
        {
            for (Producto producto : productos)
            {
                if (auxCodigo == producto.getCodigo())
                {
                    existe=true;
                    break;
                }
            }
        }
        return existe;
    }

    private  void eliminarProductoCarritoCompra()
    {
        if (ventanaMercado.getFilaSeleccionadaComprar() != -1)
        {
            if (superMercadoUV.eliminarProductoCarritoCompra(ventanaMercado.getFilaSeleccionadaComprar()))
            {
                ventanaMercado.setCodigoComprar("");
                ventanaMercado.setNombreComprar("");
                ventanaMercado.setPrecioCompraComprar("");
                ventanaMercado.setStockComprar("");
                ventanaMercado.activarTxtStockComprar();
                ventanaMercado.activarTxtCodigoComprar();
                ventanaMercado.activarTxtPrecioComprar();
                listarCarritoCompraEliminar();
                ventanaMercado.deseleccionarFilaTablaComprar();
            }
            else
            {
                ventanaMercado.mostrarMensajeError("Producto no eliminado del carrito");
            }
        }
        else
        {
            ventanaMercado.mostrarMensajeError("Seleccione un producto");
        }
    }

    private void realizarCompra()
    {
        if(ventanaMercado.getFilaSeleccionadaComprar()==-1)
        {
            ArrayList<Producto> auxCarrito = superMercadoUV.getCarritoCompra();
            if(!auxCarrito.isEmpty())
            {
                ArrayList<Producto> auxInventario = superMercadoUV.getProductos();

                Compra auxCompra;
                Proveedor auxProveedor = ventanaMercado.getProveedorComprar();
                double auxValorCompra = superMercadoUV.getPrecioTotalCompra();
                int productosCompra = superMercadoUV.getProductosTotalesCompra();
                Fecha auxFecha = new Fecha(LocalDate.now());
                auxCompra = new Compra(auxProveedor, auxValorCompra, auxCarrito, productosCompra, auxFecha);

                int stockCompra;
                int stockInventario;

                if (superMercadoUV.agregarCompra(auxCompra))
                {
                    for (Producto auxProductoCarrito : auxCarrito)
                    {
                        for (Producto auxProductoInventario : auxInventario)
                        {
                            if (auxProductoCarrito.getCodigo() == auxProductoInventario.getCodigo())
                            {
                                stockCompra = auxProductoCarrito.getStock();
                                stockInventario = auxProductoInventario.getStock();
                                auxProductoInventario.setStock(stockCompra + stockInventario);
                                ventanaMercado.setCodigoComprar("");
                                ventanaMercado.setNombreComprar("");
                                ventanaMercado.setPrecioCompraComprar("");
                                ventanaMercado.setStockComprar("");
                                ventanaMercado.setProveedorComprar(null);
                            }
                        }
                    }
                    listarComprasAgregar(auxCompra);
                    superMercadoUV.vaciarCarritoCompra();
                    listarCarritoCompraEliminarAll();
                }
                else
                {
                    ventanaMercado.mostrarMensaje("No se pudo realizar la compra");
                }
            }
            else
            {
                ventanaMercado.mostrarMensajeError("Rellene la compra con productos");
            }
        }
        else
        {
            ventanaMercado.mostrarMensajeError("Deseleccione el producto");
        }
    }

    private void actualizarIds(DefaultTableModel auxModelTabla)
    {
        for(int i=0; i<auxModelTabla.getRowCount()+1; i++)
        {
            if(auxModelTabla.getRowCount()>0)
            {
                auxModelTabla.setValueAt(i + 1, i, 0);
            }
        }
    }
    private void listarCarritoCompraAgregar(Producto auxProducto)
    {
        long auxCodigo = auxProducto.getCodigo();
        String auxNombre = auxProducto.getNombre();
        double auxPrecio = Double.parseDouble(ventanaMercado.getPrecioCompraComprar());
        int auxStock = Integer.parseInt(ventanaMercado.getStockComprar());

        DefaultTableModel auxModeloTabla = (DefaultTableModel) ventanaMercado.getModelTablaComprar();
        int auxId = auxModeloTabla.getRowCount()+1;
        auxModeloTabla.addRow(new Object[]{auxId, auxCodigo, auxNombre, auxStock, auxPrecio});
    }

    private void listarComprasAgregar(Compra auxCompra)
    {
        DefaultTableModel tablaModel = (DefaultTableModel) ventanaMercado.getModelTablaCompras();
        tablaModel.addRow(new Object[]{auxCompra.getId(), auxCompra.getNombresProductosCompra(),  auxCompra.getPreciosProductosCompra(), auxCompra.getStockProductosCompra(), Math.round(auxCompra.getValorTotal()), auxCompra.getCantidadProductos(), auxCompra.getProveedor().toString(), auxCompra.getFecha().getFecha().toString()});
    }

    private void listarCarritoCompraEliminar()
    {
        DefaultTableModel auxModeloTabla = (DefaultTableModel) ventanaMercado.getModelTablaComprar();
        int auxFila = ventanaMercado.getFilaSeleccionadaComprar();
        auxModeloTabla.removeRow(auxFila);
        actualizarIds(auxModeloTabla);
    }

    private void listarCarritoCompraEliminarAll()
    {
        DefaultTableModel auxModeloTabla = (DefaultTableModel) ventanaMercado.getModelTablaComprar();
        for(int i = 0; i < auxModeloTabla.getRowCount()+1; i++)
        {
            if(auxModeloTabla.getRowCount()>0)
            {
                auxModeloTabla.removeRow(0);
            }
        }
    }

    class TextFieldListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            try
            {
                Producto auxProducto;
                auxProducto = null;
                if (e.getActionCommand().equalsIgnoreCase("TextFieldComprar"))
                {
                    long auxCodigo = Long.parseLong(ventanaMercado.getCodigoComprar());
                    auxProducto = superMercadoUV.getProductoCodigo(auxCodigo);
                    if (auxProducto != null)
                    {
                        ventanaMercado.setNombreComprar(auxProducto.getNombre());
                        ventanaMercado.setPrecioCompraComprar("");
                        ventanaMercado.setStockComprar("");
                    } else
                    {
                        ventanaMercado.setCodigoComprar("");
                        ventanaMercado.setNombreComprar("");
                        ventanaMercado.setPrecioCompraComprar("");
                        ventanaMercado.setStockComprar("");
                        ventanaMercado.mostrarMensajeError("Producto no encontrado");
                    }
                }
                /*
                if (e.getActionCommand().equalsIgnoreCase("TextFieldVender"))
                {
                    int auxCodigo = Integer.parseInt(ventanaMercado.getCodigoVender());
                    auxProducto = superMercadoUV.getProducto(auxCodigo);
                    if (auxProducto != null)
                    {
                        ventanaMercado.setNombreVender(auxProducto.getNombre());
                        ventanaMercado.setPrecioVentaVender(String.valueOf(auxProducto.getPrecioVenta()));
                        ventanaMercado.setStockVender("");
                    } else
                    {
                        ventanaMercado.setCodigoVender("");
                        ventanaMercado.setNombreVender("");
                        ventanaMercado.setPrecioVentaVender("");
                        ventanaMercado.setStockVender("");
                        ventanaMercado.mostarMensajeError("Producto no encontrado");
                    }
                }
                 */
            } catch (NumberFormatException ex)
            {
                ventanaMercado.mostrarMensajeError("Ingrese un codigo valido");
                if (e.getActionCommand().equalsIgnoreCase("TextFieldComprar"))
                {
                    ventanaMercado.setCodigoComprar("");
                    ventanaMercado.setNombreComprar("");
                    ventanaMercado.setPrecioCompraComprar("");
                    ventanaMercado.setStockComprar("");
                }
                if (e.getActionCommand().equalsIgnoreCase("TextFieldVender"))
                {
                    ventanaMercado.setCodigoVender("");
                    ventanaMercado.setNombreVender("");
                    ventanaMercado.setStockVender("");
                    ventanaMercado.setPrecioVentaVender("");
                }
            }
        }
    }
}
