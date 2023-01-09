package controlador;

import modelo.Cliente;
import modelo.Empresa;
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

        //----------|Cliente|----------//
        ClienteListener clienteListener = new ClienteListener();
        this.ventanaMercado.addBtnAgregarClienteListener(clienteListener);
        this.ventanaMercado.addBtnEditarClienteListener(clienteListener);
        this.ventanaMercado.addBtnEliminarClienteListener(clienteListener);
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

}
