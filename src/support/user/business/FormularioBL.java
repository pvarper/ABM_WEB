package support.user.business;

import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import support.user.dao.AccionesDAO;
import support.user.dao.FormDAO;
import support.user.model.MuAccion;
import support.user.model.MuFormulario;

@Named
public class FormularioBL
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  @Inject
  private FormDAO formularioDao;
  @Inject
  private AccionesDAO accionesDAO;
  
  public String validate(MuFormulario formulario, boolean typo)
  {
    if ((formulario.getNombre() == null) || (formulario.getNombre().trim().isEmpty())) {
      return "El campo Nombre Esta Vacio...";
    }
    if (((formulario.getUrl() == null) || (formulario.getUrl().trim().isEmpty())) && (!typo)) {
      return "El campo path/url Esta Vacio...";
    }
    return "";
  }
  
  public Integer obtenerNextIdFormulario()
    throws Exception
  {
    return Integer.valueOf(this.formularioDao.getMaxIDForm().intValue() + 1);
  }
  
  public Integer obtenerNextOrdenMod()
    throws Exception
  {
    return Integer.valueOf(this.formularioDao.getMaxOrdenMod().intValue() + 1);
  }
  
  public Integer obtenerNextOrdenForm(Integer formularioId)
    throws Exception
  {
    return Integer.valueOf(this.formularioDao.getMaxOrdenForm(formularioId.intValue()).intValue() + 1);
  }
  
  public void saveFormulario(MuFormulario formulario)
    throws Exception
  {
    this.formularioDao.save(formulario);
  }
  
  public void updateFormulario(MuFormulario formulario)
    throws Exception
  {
    this.formularioDao.update(formulario);
  }
  
  public void deleteFormulario(long idForm)
    throws Exception
  {
    this.formularioDao.remove(idForm);
  }
  
  public List<MuFormulario> getModulos()
    throws Exception
  {
    return this.formularioDao.getListMod();
  }
  
  public List<MuFormulario> getPages()
    throws Exception
  {
    return this.formularioDao.getListPage();
  }
  
  public List<MuFormulario> getPages(long idModForm)
    throws Exception
  {
    return this.formularioDao.getListPage(idModForm);
  }
  
  public Integer updateOrdenMod(long idForm)
    throws Exception
  {
    MuFormulario muFormulario = this.formularioDao.get(idForm);
    if (muFormulario != null)
    {
      if (muFormulario.getUrl() != null) {
        return Integer.valueOf(this.formularioDao.getMaxOrdenMod().intValue() + 1);
      }
      return muFormulario.getOrden();
    }
    return Integer.valueOf(0);
  }
  
  public List<MuAccion> getAccionesActivas()
    throws Exception
  {
    return this.accionesDAO.getList();
  }
}
