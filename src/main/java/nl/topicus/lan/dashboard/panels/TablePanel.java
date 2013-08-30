package nl.topicus.lan.dashboard.panels;

import java.io.IOException;
import java.util.Map;

import nl.topicus.cobra.web.behaviors.ServerCallAjaxBehaviour;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.odlabs.wiquery.core.javascript.JsQuery;
import org.odlabs.wiquery.core.javascript.JsStatement;
import org.odlabs.wiquery.core.options.Options;
import org.odlabs.wiquery.ui.button.ButtonJavaScriptResourceReference;
import org.odlabs.wiquery.ui.droppable.DroppableJavaScriptResourceReference;
import org.odlabs.wiquery.ui.sortable.SortableJavaScriptResourceReference;
import org.odlabs.wiquery.ui.widget.WidgetJavaScriptResourceReference;

import com.google.common.collect.Maps;

public class TablePanel extends Panel
{

	private static final long serialVersionUID = 1L;

	public TablePanel(String id)
	{
		super(id);
		add(new ServerCallAjaxBehaviour());
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		response.render(JavaScriptHeaderItem.forReference(DroppableJavaScriptResourceReference
			.get()));
		response.render(JavaScriptHeaderItem.forReference(WidgetJavaScriptResourceReference.get()));
		response.render(JavaScriptHeaderItem.forReference(ButtonJavaScriptResourceReference.get()));
		response
			.render(JavaScriptHeaderItem.forReference(SortableJavaScriptResourceReference.get()));
		response.render(JavaScriptHeaderItem.forReference(new JavaScriptResourceReference(
			TablePanel.class, "tablepanel.js")));
		response.render(OnDomReadyHeaderItem.forScript(statement().render()));
	}

	private JsStatement statement()
	{
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Inclusion.NON_NULL);

		Options options = new Options();

		Map<String, String> tableMap = Maps.newHashMap();
		tableMap.put("1,1", "tableName1");
		tableMap.put("1,2", "tableName2");
		tableMap.put("1,3", "tableName3");
		tableMap.put("1,4", "tableName4");
		tableMap.put("1,5", "tableName5");
		tableMap.put("1,6", "tableName6");
		tableMap.put("1,7", "tableName7");

		tableMap.put("4,1", "tableName8");
		tableMap.put("4,2", "tableName9");
		tableMap.put("4,3", "tableName10");
		tableMap.put("4,4", "tableName11");
		tableMap.put("4,5", "tableName12");
		tableMap.put("4,6", "tableName13");
		tableMap.put("4,7", "tableName14");

		try
		{
			options.put("tableMap", mapper.writeValueAsString(tableMap));
			options.put("xsize", "15");
			options.put("ysize", "15");
		}
		catch (JsonGenerationException e)
		{
			throw new RuntimeException(e);
		}
		catch (JsonMappingException e)
		{
			throw new RuntimeException(e);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}

		return new JsQuery(this).$().chain("tablePanel", options.getJavaScriptOptions());
	}

}
