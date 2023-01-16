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

        //----------|Venta|-----------//
        VentaListener ventaListener = new VentaListener();
        this.ventanaMercado.addBtnRealizarVentaListener(ventaListener);
        this.ventanaMercado.addBtnAgregarVentaListener(ventaListener);
        this.ventanaMercado.addBtnEliminarVentaListener(ventaListener);
        this.ventanaMercado.addTextFieldVenderListener(textFieldListener);
        
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
        camposValido = !ventanaMercado.getNombreCliente().equals("") && !ventanaMercado.getNitCliente().equals("") && !ventanaMercado.getTelefonoCliente().equals("") && !ventanaMercado.getDireccionCliente().equals("");
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
            if (cliente.getNIT() == auxNIT && cliente.getId() != auxId)
            {
                nitValido = false;
                break;
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
                            superMercadoUV.escribirClientes();
                            listarClientesAgregar(auxCliente);
                            ventanaMercado.rellenarClienteVender(auxCliente);
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
                            superMercadoUV.escribirClientes();
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
                superMercadoUV.escribirClientes();
                listarClientesEliminar();
                ventanaMercado.eliminarClienteVender(auxCliente);
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

    //----------|Proveedores|----------//
    private boolean comprobarCamposProveedor()
    {
        boolean camposValido;
        camposValido = !ventanaMercado.getNombreProveedor().equals("") && !ventanaMercado.getNitProveedor().equals("") && !ventanaMercado.getTelefonoProveedor().equals("") && !ventanaMercado.getDireccionProveedor().equals("") && !ventanaMercado.getRazonSocialProveedor().equals("");
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
                break;
            }
        }
        return nitValido;
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

    private void agregarProveedor()
    {
        Proveedor auxProveedor;
        int auxId;
        long auxNIT;
        String auxNombre;
        long auxTelefono;
        String auxDireccion;
        String auxRazonSocial;

        auxId = Integer.parseInt(ventanaMercado.getIdProveedor());
        System.out.println(auxId);
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
                            superMercadoUV.escribirProveedores();
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
                        auxProveedor.setNit(auxNIT);
                        auxProveedor.setNombre(auxNombre);
                        auxProveedor.setTelefono(auxTelefono);
                        auxProveedor.setDireccion(auxDireccion);
                        auxProveedor.setRazonSocial(auxRazonSocial);

                        if(superMercadoUV.actualizarProveedor(auxProveedor))
                        {
                            ventanaMercado.mostrarMensaje("Proveedor editado con exito");
                            ventanaMercado.setIdProveedor("0");
                            ventanaMercado.setNitProveedor("");
                            ventanaMercado.setNombreProveedor("");
                            ventanaMercado.setTelefonoProveedor("");
                            ventanaMercado.setDireccionProveedor("");
                            ventanaMercado.setRazonSocialProveedor("");
                            superMercadoUV.escribirProveedores();
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
                superMercadoUV.escribirProveedores();
                listarProveedoresEliminar();
                ventanaMercado.eliminarProveedorComprar(auxProveedor);
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

    //----------|Producto|----------//
    private boolean comprobarCamposProducto()
    {
        boolean camposValido;
        camposValido = !ventanaMercado.getNombreProducto().equals("") && !ventanaMercado.getCodigoProducto().equals("") && !ventanaMercado.getPrecioVentaProducto().equals("") && ventanaMercado.getCategoriaProducto() != null;
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
                break;
            }
        }
        return codigoValido;
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

    private void listarProductosEditar(int auxFila ,Producto auxProducto)
    {
        int auxId = auxProducto.getId();
        long auxCodigo = auxProducto.getCodigo();
        String auxNombre = auxProducto.getNombre();
        double auxPrecioVenta = auxProducto.getPrecioVenta();
        int auxStock = auxProducto.getStock();
        String auxCategoria = auxProducto.getCategoria();

        DefaultTableModel auxModeloTabla = (DefaultTableModel) ventanaMercado.getModelTablaProducto();

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
                            superMercadoUV.escribirProductos();
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
        String auxNombre;
        double auxPrecioVenta;

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
                        superMercadoUV.escribirProductos();
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
                superMercadoUV.escribirProductos();
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
    
    private Boolean comprobarCamposCarritoCompra()
    {
        boolean camposValidos;
        camposValidos = !ventanaMercado.getCodigoComprar().equals("") && !ventanaMercado.getNombreComprar().equals("") && !ventanaMercado.getPrecioCompraComprar().equals("") && !ventanaMercado.getStockComprar().equals("");
        return camposValidos;
    }

    private void actualizarIds(DefaultTableModel auxModelTabla)
    {
        if(auxModelTabla.getRowCount() >0)
        {
            for(int i=0; i<auxModelTabla.getRowCount(); i++)
            {
                auxModelTabla.setValueAt(i + 1, i, 0);
            }
        }
    }

    private void listarCarritoComprarAgregar(Producto auxProducto)
    {
        long auxCodigo = auxProducto.getCodigo();
        String auxNombre = auxProducto.getNombre();
        double auxPrecio = auxProducto.getPrecioCompra();
        int auxStock = auxProducto.getStock();

        DefaultTableModel auxModeloTabla = (DefaultTableModel) ventanaMercado.getModelTablaComprar();
        int auxId = auxModeloTabla.getRowCount()+1;
        auxModeloTabla.addRow(new Object[]{auxId, auxCodigo, auxNombre, auxStock, auxPrecio});
    }

    private void listarComprasAgregar(Compra auxCompra)
    {
        DefaultTableModel tablaModel = (DefaultTableModel) ventanaMercado.getModelTablaCompras();
        System.out.println(auxCompra.getId() + "-" + auxCompra.getNombresProductosCompra() + "-" + auxCompra.getPreciosProductosCompra() + "-" + auxCompra.getStockProductosCompra() + "-" + Math.round(auxCompra.getValorTotal()) + "-" + auxCompra.getCantidadProductos() + "-" + auxCompra.getProveedor().toString() + "-" + auxCompra.getFecha().getFecha().toString());
        tablaModel.addRow(new Object[]{auxCompra.getId(), auxCompra.getNombresProductosCompra(),  auxCompra.getPreciosProductosCompra(), auxCompra.getStockProductosCompra(), Math.round(auxCompra.getValorTotal()), auxCompra.getCantidadProductos(), auxCompra.getProveedor().toString(), auxCompra.getFecha().getFecha().toString()});
    }

    private void listarCarritoComprarEliminar()
    {
        DefaultTableModel auxModeloTabla = (DefaultTableModel) ventanaMercado.getModelTablaComprar();
        int auxFila = ventanaMercado.getFilaSeleccionadaComprar();
        auxModeloTabla.removeRow(auxFila);
        actualizarIds(auxModeloTabla);
    }

    private void listarCarritoComprarEliminarAll()
    {
        DefaultTableModel auxModeloTabla = (DefaultTableModel) ventanaMercado.getModelTablaComprar();
        auxModeloTabla.setRowCount(0);
    }

    public void actuliazarStockCompra(ArrayList<Producto> auxCarrito)
    {
        for(Producto producto : auxCarrito)
        {
            Producto auxProducto = superMercadoUV.getProductoCodigo(producto.getCodigo());
            if(auxProducto != null)
            {
                auxProducto.aumentarStock(producto.getStock());
                listarProductosEditar(superMercadoUV.getProductos().indexOf(auxProducto), auxProducto);
            }
        }
    }

    private void agregarProductoCompra()
    {
        Producto auxProducto;
        long auxCodigo;
        String auxNombre;
        double auxPrecioCompra;
        int auxStock;

        if(ventanaMercado.getFilaSeleccionadaComprar() == -1)
        {
            if(comprobarCamposCarritoCompra())
            {
                try
                {
                    auxCodigo = Long.parseLong(ventanaMercado.getCodigoComprar());
                    auxPrecioCompra = Double.parseDouble(ventanaMercado.getPrecioCompraComprar());
                    auxStock = Integer.parseInt(ventanaMercado.getStockComprar());

                    if(auxStock > 0 && auxPrecioCompra>0)
                    {
                        if(superMercadoUV.getProductoCodigo(auxCodigo) != null)
                        {
                            auxNombre =  superMercadoUV.getProductoCodigo(auxCodigo).getNombre();
                            auxProducto = new Producto(auxCodigo, auxNombre, auxPrecioCompra, auxStock);
                            if (superMercadoUV.agregarProductoCarritoCompra(auxProducto, auxProducto.getPrecioCompra()))
                            {
                                ventanaMercado.mostrarMensaje("Producto agregado al carrito");
                                ventanaMercado.setCodigoComprar("");
                                ventanaMercado.setNombreComprar("");
                                ventanaMercado.setPrecioCompraComprar("");
                                ventanaMercado.setStockComprar("");
                                ventanaMercado.setPrecioTotalComprar("" + Math.round(superMercadoUV.getPrecioTotalCompra()));
                                ventanaMercado.setProductosTotalesComprar("" + superMercadoUV.getProductosTotalesCompra());
                                listarCarritoComprarAgregar(auxProducto);
                            }
                            else
                            {
                                ventanaMercado.mostrarMensajeError("Producto no agregado al carrito");
                            }
                        }
                        else
                        {
                            ventanaMercado.mostrarMensajeError("El producto no existe, por favor agreguelo al inventario antes de continuar");
                        }
                    }
                    else
                    {
                        ventanaMercado.mostrarMensajeError("Ingrese un stock y un precio unitario mayor a 0");
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
        else
        {
            ventanaMercado.mostrarMensajeError("Deseleccione el producto");
        }
    }

    private  void eliminarProductoCarritoCompra()
    {
        Producto auxProducto;

        if (ventanaMercado.getFilaSeleccionadaComprar() != -1)
        {
            auxProducto = superMercadoUV.getProductoCarritoCompra(ventanaMercado.getFilaSeleccionadaComprar());

            if(superMercadoUV.eliminarProductoCarritoCompra(auxProducto, auxProducto.getPrecioCompra()))
            {
                ventanaMercado.mostrarMensaje("Producto eliminado del carrito");
                ventanaMercado.setCodigoComprar("");
                ventanaMercado.setNombreComprar("");
                ventanaMercado.setPrecioCompraComprar("");
                ventanaMercado.setStockComprar("");
                ventanaMercado.setProductosTotalesComprar(String.valueOf(superMercadoUV.getProductosTotalesCompra()));
                ventanaMercado.setPrecioTotalComprar(String.valueOf(Math.round(superMercadoUV.getPrecioTotalCompra())));
                ventanaMercado.activarTxtStockComprar();
                ventanaMercado.activarTxtCodigoComprar();
                ventanaMercado.activarTxtPrecioComprar();

                listarCarritoComprarEliminar();
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
        Compra auxCompra;
        Proveedor auxProveedor;
        double auxValorCompra;
        int auxProductosTotales;
        Fecha auxFecha;

        ArrayList<Producto> auxProductosCarrito;

        if(ventanaMercado.getFilaSeleccionadaComprar() == -1)
        {
            if(ventanaMercado.getProveedorComprar() != null)
            {
                auxProductosCarrito = superMercadoUV.getCarritoCompra();
                if(!auxProductosCarrito.isEmpty())
                {
                    auxProveedor = ventanaMercado.getProveedorComprar();
                    auxValorCompra = superMercadoUV.getPrecioTotalCompra();
                    auxProductosTotales = superMercadoUV.getProductosTotalesCompra();
                    auxFecha = new Fecha(LocalDate.now());

                    auxCompra = new Compra(auxProveedor, auxValorCompra, auxProductosCarrito, auxProductosTotales, auxFecha);
                    if (superMercadoUV.agregarCompra(auxCompra))
                    {
                        ventanaMercado.mostrarMensaje("Compra realizada con exito");
                        ventanaMercado.setCodigoComprar("");
                        ventanaMercado.setNombreComprar("");
                        ventanaMercado.setPrecioCompraComprar("");
                        ventanaMercado.setStockComprar("");
                        ventanaMercado.setPrecioTotalComprar("0");
                        ventanaMercado.setProductosTotalesComprar("0");
                        ventanaMercado.setProveedorComprar(null);

                        listarComprasAgregar(auxCompra);
                        listarCarritoComprarEliminarAll();
                        actuliazarStockCompra(auxProductosCarrito);

                        superMercadoUV.escribirCompras();
                        superMercadoUV.escribirProductos();
                        superMercadoUV.vaciarCarritoCompra();
                    }
                    else
                    {
                        ventanaMercado.mostrarMensajeError("Compra no realizada");
                    }
                }
                else
                {
                    ventanaMercado.mostrarMensajeError("Rellene la compra con productos");
                }
            }
            else
            {
                ventanaMercado.mostrarMensajeError("Seleccione un proveedor");
            }
        }
        else
        {
            ventanaMercado.mostrarMensajeError("Deseleccione el producto");
        }
    }
    
    //----------|Venta|----------//
    
    class VentaListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(e.getActionCommand().equalsIgnoreCase("AGREGAR"))
            {
                agregarProductoVenta();
            }
            if(e.getActionCommand().equalsIgnoreCase("VENDER"))
            {
                realizarVenta();
            }
            if(e.getActionCommand().equalsIgnoreCase("ELIMINAR"))
            {
                eliminarProductoCarritoVenta();
            }
        }
    }
    
    private boolean comprobarCamposCarritoVenta()
    {
        boolean camposValidos;
        camposValidos = !ventanaMercado.getCodigoVender().equals("") && !ventanaMercado.getStockVender().equals("");
        return camposValidos;
    }

    private void listarCarritoVenderAgregar(Producto auxProducto)
    {
        long auxCodigo = auxProducto.getCodigo();
        String auxNombre = auxProducto.getNombre();
        double auxPrecioVenta = auxProducto.getPrecioVenta();
        int auxStock = auxProducto.getStock();

        DefaultTableModel auxModeloTabla = (DefaultTableModel) ventanaMercado.getModelTablaVender();
        int auxId = auxModeloTabla.getRowCount()+1;
        auxModeloTabla.addRow(new Object[]{auxId, auxCodigo, auxNombre, auxStock, auxPrecioVenta});
    }

    private void listarVentasAgregar(Venta auxVenta)
    {
        DefaultTableModel tablaModel = (DefaultTableModel) ventanaMercado.getModelTablaVentas();
        System.out.println(auxVenta.getId() + "-" + auxVenta.getNombresProductosVenta() + "-" + auxVenta.getPreciosProductosVenta() + "-" + auxVenta.getStockProductosVenta() + "-" + Math.round(auxVenta.getValorTotal()) + "-" + auxVenta.getCantidadProductos() + "-" + auxVenta.getCliente().toString() + "-" + auxVenta.getFecha().getFecha().toString());
        tablaModel.addRow(new Object[]{auxVenta.getId(), auxVenta.getNombresProductosVenta(),  auxVenta.getPreciosProductosVenta(), auxVenta.getStockProductosVenta(), Math.round(auxVenta.getValorTotal()), auxVenta.getCantidadProductos(), auxVenta.getCliente().toString(), auxVenta.getFecha().getFecha().toString()});
    }

    private void listarCarritoVenderEliminar()
    {
        DefaultTableModel auxModeloTabla = (DefaultTableModel) ventanaMercado.getModelTablaVender();
        int auxFila = ventanaMercado.getFilaSeleccionadaVender();
        auxModeloTabla.removeRow(auxFila);
        actualizarIds(auxModeloTabla);
    }

    private void listarCarritoVenderEliminarAll()
    {
        DefaultTableModel auxModeloTabla = (DefaultTableModel) ventanaMercado.getModelTablaVender();
        auxModeloTabla.setRowCount(0);
    }

    public void actualizarStockVenta(ArrayList<Producto> auxCarrito)
    {
        for(Producto producto : auxCarrito)
        {
            Producto auxProducto = superMercadoUV.getProductoCodigo(producto.getCodigo());
            if(auxProducto != null)
            {
                auxProducto.disminuirStock(producto.getStock());
                listarProductosEditar(superMercadoUV.getProductos().indexOf(auxProducto), auxProducto);
            }
        }
    }

    private void agregarProductoVenta()
    {
        Producto auxProducto;
        long auxCodigo;
        String auxNombre;
        double auxPrecioVenta;
        int auxStock;

        if(ventanaMercado.getFilaSeleccionadaVender() == -1)
        {
            if(comprobarCamposCarritoVenta())
            {
                try
                {
                    auxCodigo = Long.parseLong(ventanaMercado.getCodigoVender());
                    auxStock = Integer.parseInt(ventanaMercado.getStockVender());

                    if(auxStock > 0)
                    {
                        if(auxStock <= superMercadoUV.getProductoCodigo(auxCodigo).getStock())
                        {
                            if(superMercadoUV.getProductoCodigo(auxCodigo) != null)
                            {
                                auxNombre =  superMercadoUV.getProductoCodigo(auxCodigo).getNombre();
                                auxPrecioVenta = superMercadoUV.getProductoCodigo(auxCodigo).getPrecioVenta();
                                auxProducto = new Producto(auxCodigo, auxNombre, auxStock, auxPrecioVenta);

                                if (superMercadoUV.agregarProductoCarritoVenta(auxProducto, auxProducto.getPrecioVenta()))
                                {
                                    ventanaMercado.mostrarMensaje("Producto agregado al carrito");
                                    ventanaMercado.setCodigoVender("");
                                    ventanaMercado.setNombreVender("");
                                    ventanaMercado.setPrecioVentaVender("");
                                    ventanaMercado.setStockVender("");
                                    ventanaMercado.setPrecioTotalVender("" + Math.round(superMercadoUV.getPrecioTotalVenta()));
                                    ventanaMercado.setProductosTotalesVender("" + superMercadoUV.getProductosTotalesVenta());
                                    listarCarritoVenderAgregar(auxProducto);
                                }
                                else
                                {
                                    ventanaMercado.mostrarMensajeError("Producto no agregado al carrito");
                                }
                            }
                            else
                            {
                                ventanaMercado.mostrarMensajeError("El producto no existe, por favor agreguelo al inventario antes de continuar");
                            }
                        }
                        else
                        {
                            ventanaMercado.mostrarMensajeError("Cantidad de este producto no disponible");
                        }
                    }
                    else
                    {
                        ventanaMercado.mostrarMensajeError("Ingrese un stock mayor a 0");
                    }
                }
                catch (NumberFormatException e)
                {
                    ventanaMercado.mostrarMensajeError("Ingrese valores validos en los campos codigo y stock");
                }
            }
            else
            {
                ventanaMercado.mostrarMensajeError("Rellene los campos correctamente");
            }
        }
        else
        {
            ventanaMercado.mostrarMensajeError("Deseleccione el producto");
        }
    }

    private  void eliminarProductoCarritoVenta()
    {
        Producto auxProducto;

        if (ventanaMercado.getFilaSeleccionadaVender() != -1)
        {
            auxProducto = superMercadoUV.getProductoCarritoVenta(ventanaMercado.getFilaSeleccionadaVentas());

            if(superMercadoUV.eliminarProductoCarritoVenta(auxProducto, auxProducto.getPrecioVenta()))
            {
                ventanaMercado.mostrarMensaje("Producto eliminado del carrito");
                ventanaMercado.setCodigoVender("");
                ventanaMercado.setNombreVender("");
                ventanaMercado.setPrecioVentaVender("");
                ventanaMercado.setStockVender("");
                ventanaMercado.setProductosTotalesVender(String.valueOf(superMercadoUV.getProductosTotalesVenta()));
                ventanaMercado.setPrecioTotalVender(String.valueOf(Math.round(superMercadoUV.getPrecioTotalVenta())));
                ventanaMercado.activarTxtStockVender();

                listarCarritoVenderEliminar();
                ventanaMercado.deseleccionarFilaTablaVender();
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

    private void realizarVenta()
    {
        Venta auxVenta;
        Cliente auxCliente;
        double auxValorVenta;
        int auxProductosTotales;
        Fecha auxFecha;

        ArrayList<Producto> auxProductosCarrito;

        if(ventanaMercado.getFilaSeleccionadaVender() == -1)
        {
            auxCliente = ventanaMercado.getClienteVender();
            if(ventanaMercado.getClienteVender() != null)
            {
                auxProductosCarrito = superMercadoUV.getCarritoVenta();
                if(!auxProductosCarrito.isEmpty())
                {
                    auxValorVenta = superMercadoUV.getPrecioTotalVenta();
                    auxProductosTotales = superMercadoUV.getProductosTotalesVenta();
                    auxFecha = new Fecha(LocalDate.now());

                    auxVenta = new Venta(auxCliente, auxValorVenta, auxProductosCarrito, auxProductosTotales, auxFecha);
                    if (superMercadoUV.agregarVenta(auxVenta))
                    {
                        ventanaMercado.mostrarMensaje("Compra realizada con exito");
                        ventanaMercado.setCodigoVender("");
                        ventanaMercado.setNombreVender("");
                        ventanaMercado.setPrecioVentaVender("");
                        ventanaMercado.setStockVender("");
                        ventanaMercado.setPrecioTotalVender("0");
                        ventanaMercado.setProductosTotalesVender("0");
                        ventanaMercado.setClienteVender(null);

                        listarVentasAgregar(auxVenta);
                        listarCarritoVenderEliminarAll();
                        actualizarStockVenta(auxProductosCarrito);

                        superMercadoUV.escribirVentas();
                        superMercadoUV.escribirProductos();
                        superMercadoUV.vaciarCarritoVenta();
                    }
                    else
                    {
                        ventanaMercado.mostrarMensajeError("Compra no realizada");
                    }
                }
                else
                {
                    ventanaMercado.mostrarMensajeError("Rellene la compra con productos");
                }
            }
            else
            {
                ventanaMercado.mostrarMensajeError("Seleccione un cliente");
            }
        }
        else
        {
            ventanaMercado.mostrarMensajeError("Deseleccione el producto");
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
                        ventanaMercado.mostrarMensajeError("Producto no encontrado");
                    }
                }
                 
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
    //----------|Conexion|----------//
    public void recuperarDatos()
    {
        if(superMercadoUV.recuperarDatos())
        {
            if(!superMercadoUV.getClientes().isEmpty())
            {
                for(Cliente cliente : superMercadoUV.getClientes())
                {
                    listarClientesAgregar(cliente);
                    ventanaMercado.rellenarClienteVender(cliente);
                }
            }
            if(!superMercadoUV.getProveedores().isEmpty())
            {
                for(Proveedor proveedor : superMercadoUV.getProveedores())
                {
                    listarProveedoresAgregar(proveedor);
                    ventanaMercado.rellenarProveedorComprar(proveedor);
                }
            }
            if(!superMercadoUV.getProductos().isEmpty())
            {
                for(Producto producto : superMercadoUV.getProductos())
                {
                    listarProductosAgregar(producto);
                }
            }
            if(!superMercadoUV.getCompras().isEmpty())
            {
                for(Compra compra : superMercadoUV.getCompras())
                {
                    listarComprasAgregar(compra);
                }
            }
            if(!superMercadoUV.getVentas().isEmpty())
            {
                for(Venta venta : superMercadoUV.getVentas())
                {
                    listarVentasAgregar(venta);
                }
            }
        }
        else
        {
            ventanaMercado.mostrarMensajeError("Error a recuperar los datos. Hace falta informacion. Porfavor cargue el backUp mas reciente");
        }
    }
}
