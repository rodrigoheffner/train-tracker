package org.rodrigobezerra;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.rodrigobezerra.model.TrainRoute;

public class HomePage extends WebPage {

    private static final long serialVersionUID = 1L;

    public HomePage(final PageParameters parameters) {
        super(parameters);

        add(new ListView<TrainRoute>("trainRoutesListView", createTrainRoutesModel()) {

            @Override
            protected void populateItem(ListItem<TrainRoute> li) {
                li.setOutputMarkupId(true);
                
                li.add(new Label("from", li.getModelObject().getFrom()));
                li.add(new Label("to", li.getModelObject().getTo()));
                li.add(new Label("std", li.getModelObject().getTrainTime().getStandardDepartureTime()));
                li.add(new Label("eta", li.getModelObject().getTrainTime().getEstimatedDepartureTime()));
//                li.add(new Label("response", li.getModelObject().getTrainTime().getResponse()));
                WebMarkupContainer responseLink = new WebMarkupContainer("response");
                responseLink.add(new AttributeAppender("data-content", li.getModelObject().getTrainTime().getResponse()));
                li.add(responseLink);
            }
        });

    }

    private List<TrainRoute> createTrainRoutesModel() {
        List<TrainRoute> trainRoutes = new ArrayList<TrainRoute>();

        trainRoutes.add(new TrainRoute(Constants.STATION_CODE__CARDIFF_CENTRAL,
                Constants.STATION_CODE__LLANTWIT_MAJOR));
        trainRoutes.add(new TrainRoute(Constants.STATION_CODE__CARDIFF_CENTRAL,
                Constants.STATION_CODE__BRIDGEND));
        trainRoutes.add(new TrainRoute(Constants.STATION_CODE__CARDIFF_QUEEN_STREET,
                Constants.STATION_CODE__LLANTWIT_MAJOR));
        trainRoutes.add(new TrainRoute(Constants.STATION_CODE__CARDIFF_QUEEN_STREET,
                Constants.STATION_CODE__BRIDGEND));
        trainRoutes.add(new TrainRoute(Constants.STATION_CODE__LLANTWIT_MAJOR,
                Constants.STATION_CODE__BRIDGEND));
        trainRoutes.add(new TrainRoute(Constants.STATION_CODE__LLANTWIT_MAJOR,
                Constants.STATION_CODE__CARDIFF_QUEEN_STREET));
        trainRoutes.add(new TrainRoute(Constants.STATION_CODE__BRIDGEND,
                Constants.STATION_CODE__LLANTWIT_MAJOR));

        return trainRoutes;
    }
}
