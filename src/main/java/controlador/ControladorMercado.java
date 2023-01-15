package controlador;

import modelo.*;
import vista.VentanaMercado;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
        
        //----------|Proveedor|----------//
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
                            ventanaMercado.mostarMensaje("Cliente agregado con exito");
                            ventanaMercado.setIdCliente("0");
                            ventanaMercado.setNitCliente("");
                            ventanaMercado.setNombreCliente("");
                            ventanaMercado.setTelefonoCliente("");
                            ventanaMercado.setDireccionCliente("");
                            listarClientesAgregar(auxCliente);
                        }
                        else
                        {
                            ventanaMercado.mostarMensajeError("Cliente agregado sin exito");
                        }
                    }
                    else
                    {
                        ventanaMercado.mostarMensajeError("NIT ya registrado");
                    }
                }
                catch (NumberFormatException e)
                {
                    ventanaMercado.mostarMensajeError("Ingrese numeros enteros en los campos NIT y Telefono");
                }
            }
            else
            {
                ventanaMercado.mostarMensajeError("Rellene todos los campos");
            }
        }
        else
        {
            ventanaMercado.mostarMensajeError("Deseleccione el cliente");
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
                            ventanaMercado.mostarMensaje("Cliente editado con exito");
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
                            ventanaMercado.mostarMensajeError("Cliente editado sin exito");
                        }
                    }
                    else
                    {
                        ventanaMercado.mostarMensajeError("NIT ya registrado");
                    }
                }
                catch (NumberFormatException e)
                {
                    ventanaMercado.mostarMensajeError("Ingrese numeros enteros en los campos NIT y Telefono");
                }
            }
            else
            {
                ventanaMercado.mostarMensajeError("Rellene todos los campos");
            }
        }
        else
        {
            ventanaMercado.mostarMensajeError("Seleccione a un cliente");
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
                ventanaMercado.mostarMensaje("Cliente eliminado con exito");
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
                ventanaMercado.mostarMensajeError("Cliente eliminado sin exito");
            }
        }
        else
        {
            ventanaMercado.mostarMensajeError("Seleccione a un cliente");
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
                            ventanaMercado.mostarMensaje("Proveedor agregado con exito");
                            ventanaMercado.setIdProveedor("0");
                            ventanaMercado.setNitProveedor("");
                            ventanaMercado.setNombreProveedor("");
                            ventanaMercado.setTelefonoProveedor("");
                            ventanaMercado.setDireccionProveedor("");
                            ventanaMercado.setRazonSocialProveedor("");
                            listarProveedoresAgregar(auxProveedor);
                        }
                        else
                        {
                            ventanaMercado.mostarMensajeError("Proveedor no agregado");
                        }
                    }
                    else
                    {
                        ventanaMercado.mostarMensajeError("NIT ya registrado");
                    }
                }
                catch (NumberFormatException e)
                {
                    ventanaMercado.mostarMensajeError("Ingrese numeros enteros en los campos NIT y Telefono");
                }
            }
            else
            {
                ventanaMercado.mostarMensajeError("Rellene todos los campos");
            }
        }
        else
        {
            ventanaMercado.mostarMensajeError("Deseleccione el proveedor");
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
                            ventanaMercado.mostarMensaje("Proveedor editado con exito");
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
                            ventanaMercado.mostarMensajeError("Proveedor no editado");
                        }
                    }
                    else
                    {
                        ventanaMercado.mostarMensajeError("NIT ya registrado");
                    }
                }
                catch (NumberFormatException e)
                {
                    ventanaMercado.mostarMensajeError("Ingrese numeros enteros en los campos NIT y Telefono");
                }
            }
            else
            {
                ventanaMercado.mostarMensajeError("Rellene todos los campos");
            }
        }
        else
        {
            ventanaMercado.mostarMensajeError("Seleccione un proveedor");
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
                ventanaMercado.mostarMensaje("Proveedor eliminado con exito");
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
                ventanaMercado.mostarMensajeError("Proveedor no eliminado");
            }
        }
        else
        {
            ventanaMercado.mostarMensajeError("Seleccione a un proveedor");
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
    
    //----------|Proveedor|----------//
    
    class ProductoListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (e.getActionCommand().equalsIgnoreCase("AGREGAR"))
            {
                agregarProducto();
                System.out.print(superMercadoUV.getProductos());
            }
            if (e.getActionCommand().equalsIgnoreCase("EDITAR"))
            {
                editarProducto();
                System.out.print(superMercadoUV.getProductos());
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
        if(ventanaMercado.getNombreProducto().equals("") || ventanaMercado.getCodigoProducto().equals("") || ventanaMercado.getPrecioVentaProducto().equals("") || ventanaMercado.getCategoriaProducto().equals(""))
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
                        auxProducto = new Producto(auxCodigo, auxNombre, auxPrecioVenta, auxCategoria);
                        if(superMercadoUV.agregarProducto(auxProducto))
                        {
                            ventanaMercado.mostarMensaje("Producto agregado con exito");
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
                            ventanaMercado.mostarMensajeError("Producto no agregado");
                        }
                    }
                    else
                    {
                        ventanaMercado.mostarMensajeError("Codigo ya registrado");
                    }
                }
                catch (NumberFormatException e)
                {
                    ventanaMercado.mostarMensajeError("Ingrese numeros enteros en el campo Codigo y Precio Venta");
                }
            }
            else
            {
                ventanaMercado.mostarMensajeError("Rellene todos los campos");
            }
        }
        else
        {
            ventanaMercado.mostarMensajeError("Deseleccione el producto");
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
                        ventanaMercado.mostarMensaje("Producto editado con exito");
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
                        ventanaMercado.mostarMensajeError("Producto no editado");
                    }
                }
                catch (NumberFormatException e)
                {
                    ventanaMercado.mostarMensajeError("Ingrese numeros enteros en el campo Codigo y Precio Venta");
                }
            }
            else
            {
                ventanaMercado.mostarMensajeError("Rellene todos los campos");
            }
        }
        else
        {
            ventanaMercado.mostarMensajeError("Seleccione un producto");
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
                ventanaMercado.mostarMensaje("Producto eliminado con exito");
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
                ventanaMercado.mostarMensajeError("Producto no eliminado");
            }
        }
        else
        {
            ventanaMercado.mostarMensajeError("Seleccione un producto");
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
}
