package hu.eisys.release.presenter;

import javax.inject.Inject;
import javax.swing.JFrame;
import hu.eisys.release.presenter.CDIEvent.*;

@SuppressWarnings("serial")
public class ViewComponent extends JFrame {
	@Inject
	private javax.enterprise.event.Event<ParameterDTO> viewEvent;

	public void fireViewEvent(final String methodIdentifier, final Object primaryParameter,
			final Object... secondaryParameters) {
		viewEvent.select(new CDIEventImpl(methodIdentifier))
				.fire(new ParameterDTO(primaryParameter, secondaryParameters));
	}
}
