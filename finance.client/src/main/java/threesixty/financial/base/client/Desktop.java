package threesixty.financial.base.client;

import java.beans.PropertyChangeEvent;
import java.security.AccessController;
import java.security.Principal;
import java.util.List;

import javax.security.auth.Subject;

import org.eclipse.scout.rt.client.session.ClientSessionProvider;
import org.eclipse.scout.rt.client.ui.action.keystroke.IKeyStroke;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.basic.filechooser.FileChooser;
import org.eclipse.scout.rt.client.ui.desktop.AbstractDesktop;
import org.eclipse.scout.rt.client.ui.desktop.bookmark.menu.AbstractBookmarkMenu;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutlineViewButton;
import org.eclipse.scout.rt.client.ui.desktop.outline.IOutline;
import org.eclipse.scout.rt.client.ui.form.ScoutInfoForm;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.status.IStatus;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;

import com.github.markash.threesixty.financial.client.finance.FinanceOutline;
import com.github.markash.threesixty.financial.client.operations.OperationsOutline;
import com.github.markash.threesixty.financial.shared.Icons;
import com.github.markash.threesixty.financial.shared.operations.IImportTransactionsService;
import com.github.markash.threesixty.financial.shared.operations.ImportTransactionLineParser;

import threesixty.financial.base.client.Desktop.UserProfileMenu.ThemeMenu.DarkThemeMenu;
import threesixty.financial.base.client.Desktop.UserProfileMenu.ThemeMenu.DefaultThemeMenu;
import threesixty.financial.base.client.search.SearchOutline;
import threesixty.financial.base.client.settings.SettingsOutline;

import org.eclipse.scout.rt.platform.classid.ClassId;

/**
 * 
 * @author Mark P Ashworth (mp.ashworth@gmail.com)
 *
 */
public class Desktop extends AbstractDesktop {

	public Desktop() {
		addPropertyChangeListener(PROP_THEME, this::onThemeChanged);
	}

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("ApplicationTitle");
	}

	@Override
	protected String getConfiguredLogoId() {
		return Icons.AppLogo;
	}

	@Override
	protected boolean getConfiguredDense() {
	    return true;
	}
	
	@Override
	protected List<Class<? extends IOutline>> getConfiguredOutlines() {
		return CollectionUtility.<Class<? extends IOutline>>arrayList(
		    FinanceOutline.class, 
		    SearchOutline.class,
		    OperationsOutline.class,
				SettingsOutline.class
			);
	}

	@Override
	protected void execDefaultView() {
		selectFirstVisibleOutline();
	}

	protected void selectFirstVisibleOutline() {
	  
		for (IOutline outline : getAvailableOutlines()) {
			if (outline.isEnabled() && outline.isVisible()) {
				setOutline(outline.getClass());
				return;
			}
		}
	}

	protected void onThemeChanged(PropertyChangeEvent evt) {
		IMenu darkMenu = getMenuByClass(DarkThemeMenu.class);
		IMenu defaultMenu = getMenuByClass(DefaultThemeMenu.class);
		String newThemeName = (String) evt.getNewValue();
		if (DarkThemeMenu.DARK_THEME.equalsIgnoreCase(newThemeName)) {
			darkMenu.setIconId(Icons.CheckedBold);
			defaultMenu.setIconId(null);
		} else {
			darkMenu.setIconId(null);
			defaultMenu.setIconId(Icons.CheckedBold);
		}
	}

	@Order(10)
	@ClassId("9314f1ba-9531-4286-bc90-5c5ca701c1ac")
	public class BookmarkMenu extends AbstractBookmarkMenu {
	    
	    @Override
        protected final String getConfiguredText() {
            
            return TEXTS.get("Bookmark");
        }
	}
	
	@Order(10)
    @ClassId("9a97ff59-136c-4931-81a5-7ef80407ade2")
    public class QuickAccessMenu extends AbstractMenu {

        @Override
        protected String getConfiguredText() {
            return TEXTS.get("QuickAccess");
        }

        @Order(10)
        @ClassId("b2a41243-726c-4375-92fd-79bfcba4fe68")
        public class ImportTransactionsMenu extends AbstractMenu {

            @Override
            protected String getConfiguredText() {
                return TEXTS.get("ImportTransactions");
            }

            @Override
            protected void execAction() {

                List<BinaryResource> files = new FileChooser().startChooser();

                IImportTransactionsService service = BEANS.get(IImportTransactionsService.class);

                try {
                    for (BinaryResource file : files) {

                        service.importFile(new ImportTransactionLineParser().apply(file));
                    }
                } catch (Exception e) {
                    new MessageBox().withHeader("Import error").withBody(e.getMessage()).withSeverity(IStatus.ERROR)
                            .show();
                }
            }
        }
    }
	
	@Order(1000)
	@ClassId("a92ba36a-b1d3-4035-a38d-ffc6245f3e60")
	public class UserProfileMenu extends AbstractMenu {

		@Override
		protected String getConfiguredKeyStroke() {
			return IKeyStroke.F10;
		}

		@Override
		protected String getConfiguredIconId() {
			return Icons.PersonSolid;
		}

		@Override
		protected String getConfiguredText() {
			Subject subject = Subject.getSubject(AccessController.getContext());
			Principal firstPrincipal = CollectionUtility.firstElement(subject.getPrincipals());
			return StringUtility.uppercaseFirst(firstPrincipal.getName());
		}

		@Order(1000)
		@ClassId("81456a27-b3fa-4a62-ad98-0837ea0aed83")
		public class AboutMenu extends AbstractMenu {

			@Override
			protected String getConfiguredText() {
				return TEXTS.get("About");
			}

			@Override
			protected void execAction() {
				ScoutInfoForm form = new ScoutInfoForm();
				form.startModify();
			}
		}

		@Order(2000)
		@ClassId("fd5cce76-d720-4b81-a8af-540e6313eba7")
		public class ThemeMenu extends AbstractMenu {

			@Override
			protected String getConfiguredText() {
				return TEXTS.get("Theme");
			}

			@Order(1000)
			@ClassId("67a82504-de60-4226-a6a3-a1cffff900d4")
			public class DefaultThemeMenu extends AbstractMenu {

				private static final String DEFAULT_THEME = "Default";

				@Override
				protected String getConfiguredText() {
					return DEFAULT_THEME;
				}

				@Override
				protected void execAction() {
					setTheme(DEFAULT_THEME.toLowerCase());
				}
			}

			@Order(2000)
			@ClassId("9b7aa236-703b-4d4f-b929-3d9bc85ed8f6")
			public class DarkThemeMenu extends AbstractMenu {

				private static final String DARK_THEME = "Dark";

				@Override
				protected String getConfiguredText() {
					return DARK_THEME;
				}

				@Override
				protected void execAction() {
					setTheme(DARK_THEME.toLowerCase());
				}
			}
		}

		@Order(3000)
		@ClassId("a0e5d65e-d7f7-404c-ad35-9f172782fe1c")
		public class LogoutMenu extends AbstractMenu {

			@Override
			protected String getConfiguredText() {
				return TEXTS.get("Logout");
			}

			@Override
			protected void execAction() {
				ClientSessionProvider.currentSession().stop();
			}
		}
	}

	@Order(1000)
	@ClassId("739077fc-89d8-4e4e-a880-d7f50caf62c6")
	public class FinanceOutlineViewButton extends AbstractOutlineViewButton {

		public FinanceOutlineViewButton() {
			this(FinanceOutline.class);
		}

		protected FinanceOutlineViewButton(Class<? extends FinanceOutline> outlineClass) {
			super(Desktop.this, outlineClass);
		}

		@Override
		protected String getConfiguredKeyStroke() {
			return IKeyStroke.F2;
		}
	}

	@Order(2000)
	@ClassId("b07c95af-71de-495b-a5d2-a0293cfc3c6a")
	public class SearchOutlineViewButton extends AbstractOutlineViewButton {

		public SearchOutlineViewButton() {
			this(SearchOutline.class);
		}

		protected SearchOutlineViewButton(Class<? extends SearchOutline> outlineClass) {
			super(Desktop.this, outlineClass);
		}

		@Override
		protected DisplayStyle getConfiguredDisplayStyle() {
			return DisplayStyle.TAB;
		}

		@Override
		protected String getConfiguredKeyStroke() {
			return IKeyStroke.F3;
		}
	}

	@Order(3000)
  @ClassId("e7d80710-d01c-4850-86b1-77e1c0de48bc")
  public class OperationsOutlineViewButton extends AbstractOutlineViewButton {

    public OperationsOutlineViewButton() {
      this(OperationsOutline.class);
    }

    protected OperationsOutlineViewButton(Class<? extends OperationsOutline> outlineClass) {
      super(Desktop.this, outlineClass);
    }

    @Override
    protected DisplayStyle getConfiguredDisplayStyle() {
      return DisplayStyle.TAB;
    }

    @Override
    protected String getConfiguredKeyStroke() {
      return IKeyStroke.F5;
    }
  }
	
	@Order(4000)
	@ClassId("cb6bf6ed-42f3-46a2-92a1-8df979797e8a")
	public class SettingsOutlineViewButton extends AbstractOutlineViewButton {

		public SettingsOutlineViewButton() {
			this(SettingsOutline.class);
		}

		protected SettingsOutlineViewButton(Class<? extends SettingsOutline> outlineClass) {
			super(Desktop.this, outlineClass);
		}

		@Override
		protected DisplayStyle getConfiguredDisplayStyle() {
			return DisplayStyle.TAB;
		}

		@Override
		protected String getConfiguredKeyStroke() {
			return IKeyStroke.F10;
		}
	}
}
