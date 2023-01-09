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
    private final VentanaMercado a;

    public ControladorMercado(Empresa auxEmpresa, VentanaMercado auxA)
    {
        this.superMercadoUV = auxEmpresa;
        this.a = auxA;
        a.setVisible(true);

        //----------|Cliente|----------//
        ClienteListener clienteListener = new ClienteListener();
        this.a.addBtnAgregarClienteListener(clienteListener);
        this.a.addBtnEditarClienteListener(clienteListener);
        this.a.addBtnEliminarClienteListener(clienteListener);
    }

    //==========|Clientes|==========//
    private boolean comprobarCamposCliente()
    {
        boolean camposValido;
        camposValido = true;
        if(a.getNombreCliente().equals("") || a.getNitCliente().equals("") || a.getTelefonoCliente().equals("") || a.getDireccionCliente().equals(""))
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

        DefaultTableModel auxModeloTabla = (DefaultTableModel) a.getModelTablaCliente();
        auxModeloTabla.addRow(new Object[]{auxId, auxNit, auxNombre, auxTelefono, auxDireccion});
    }

    private void listarClientesEditar(Cliente auxCliente)
    {
        int auxId = auxCliente.getId();
        long auxNit = auxCliente.getNIT();
        String auxNombre = auxCliente.getNombre();
        long auxTelefono = auxCliente.getTelefono();
        String auxDireccion = auxCliente.getDireccion();

        DefaultTableModel auxModeloTabla = (DefaultTableModel) a.getModelTablaCliente();
        int auxFila = a.getFilaSeleccionadaCliente();

        auxModeloTabla.setValueAt(auxId, auxFila, 0);
        auxModeloTabla.setValueAt(auxNit, auxFila, 1);
        auxModeloTabla.setValueAt(auxNombre, auxFila, 2);
        auxModeloTabla.setValueAt(auxTelefono, auxFila, 3);
        auxModeloTabla.setValueAt(auxDireccion, auxFila, 4);
    }

    private void listarClientesEliminar()
    {
        DefaultTableModel auxModeloTabla = (DefaultTableModel) a.getModelTablaCliente();
        int auxFila = a.getFilaSeleccionadaCliente();
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

        auxId = Integer.parseInt(a.getIdCliente());
        if(auxId == 0)
        {
            if(comprobarCamposCliente())
            {
                try
                {
                    auxNIT = Long.parseLong(a.getNitCliente());
                    auxNombre = a.getNombreCliente();
                    auxTelefono = Long.parseLong(a.getTelefonoCliente());
                    auxDireccion = a.getDireccionCliente();

                    if(comprobarNITCliente(0 ,auxNIT))
                    {
                        auxCliente = new Cliente(auxNIT, auxNombre, auxTelefono, auxDireccion);
                        if(superMercadoUV.agregarCliente(auxCliente))
                        {
                            a.mostarMensaje("Cliente agregado con exito");
                            a.setIdCliente("0");
                            a.setNitCliente("");
                            a.setNombreCliente("");
                            a.setTelefonoCliente("");
                            a.setDireccionCliente("");
                            listarClientesAgregar(auxCliente);
                        }
                        else
                        {
                            a.mostarMensajeError("Cliente agregado sin exito");
                        }
                    }
                    else
                    {
                        a.mostarMensajeError("NIT ya registrado");
                    }
                }
                catch (NumberFormatException e)
                {
                    a.mostarMensajeError("Ingrese numeros enteros en los campos NIT y Telefono");
                }
            }
            else
            {
                a.mostarMensajeError("Rellene todos los campos");
            }
        }
        else
        {
            a.mostarMensajeError("Deseleccione el cliente");
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

        auxId = Integer.parseInt(a.getIdCliente());
        auxCliente = superMercadoUV.getCliente(auxId);

        if(auxCliente != null)
        {
            if(comprobarCamposCliente())
            {
                try
                {
                    auxNIT = Long.parseLong(a.getNitCliente());
                    auxNombre = a.getNombreCliente();
                    auxTelefono = Long.parseLong(a.getTelefonoCliente());
                    auxDireccion = a.getDireccionCliente();

                    if(comprobarNITCliente(auxId, auxNIT))
                    {
                        auxCliente.setNIT(auxNIT);
                        auxCliente.setNombre(auxNombre);
                        auxCliente.setTelefono(auxTelefono);
                        auxCliente.setDireccion(auxDireccion);

                        if(superMercadoUV.actualizarCliente(auxCliente))
                        {
                            a.mostarMensaje("Cliente editado con exito");
                            a.setIdCliente("0");
                            a.setNitCliente("");
                            a.setNombreCliente("");
                            a.setTelefonoCliente("");
                            a.setDireccionCliente("");
                            listarClientesEditar(auxCliente);
                            a.deseleccionarFilaTablaCliente();
                        }
                        else
                        {
                            a.mostarMensajeError("Cliente editado sin exito");
                        }
                    }
                    else
                    {
                        a.mostarMensajeError("NIT ya registrado");
                    }
                }
                catch (NumberFormatException e)
                {
                    a.mostarMensajeError("Ingrese numeros enteros en los campos NIT y Telefono");
                }
            }
            else
            {
                a.mostarMensajeError("Rellene todos los campos");
            }
        }
        else
        {
            a.mostarMensajeError("Seleccione a un cliente");
        }
    }

    private void eliminarCliente()
    {
        Cliente auxCliente;
        int auxId;
        auxId = Integer.parseInt(a.getIdCliente());
        auxCliente = superMercadoUV.getCliente(auxId);

        if(auxCliente != null)
        {
            if(superMercadoUV.eliminarCliente(auxCliente))
            {
                a.mostarMensaje("Cliente eliminado con exito");
                a.setIdCliente("0");
                a.setNitCliente("");
                a.setNombreCliente("");
                a.setTelefonoCliente("");
                a.setDireccionCliente("");
                listarClientesEliminar();
                a.deseleccionarFilaTablaCliente();
            }
            else
            {
                a.mostarMensajeError("Cliente eliminado sin exito");
            }
        }
        else
        {
            a.mostarMensajeError("Seleccione a un cliente");
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
