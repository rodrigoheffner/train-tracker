package org.rodrigobezerra;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.behavior.Behavior;
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
                
                li.add(new Label("from", li.getModelObject().getFrom())
                        .add(getClassAppender(li.getModelObject().getFrom()))
                        .add(getTitleAppender(li.getModelObject().getFrom())));
                li.add(new Label("to", li.getModelObject().getTo())
                        .add(getClassAppender(li.getModelObject().getTo()))
                        .add(getTitleAppender(li.getModelObject().getTo())));
                li.add(new Label("std", li.getModelObject().getTrainTime().getStandardDepartureTime()));
                li.add(new Label("eta", li.getModelObject().getTrainTime().getEstimatedDepartureTime()));
                
                WebMarkupContainer responseLink = new WebMarkupContainer("response");
                responseLink.add(new AttributeAppender("data-content", li.getModelObject().getTrainTime().getResponse()));
                li.add(responseLink);
            }

            private AttributeAppender getClassAppender(String stationCode) {
                String className = "label label-";
                
                if (stationCode.equals(Constants.STATION_CODE__BRIDGEND)) {
                    className = className + "default";
                } else if (stationCode.equals(Constants.STATION_CODE__CARDIFF_CENTRAL)) {
                    className = className + "warning";
                } else if (stationCode.equals(Constants.STATION_CODE__CARDIFF_QUEEN_STREET)) {
                    className = className + "success";
                } else if (stationCode.equals(Constants.STATION_CODE__LLANTWIT_MAJOR)) {
                    className = className + "primary";
                }
                
                return new AttributeAppender("class", className);
            }

            private AttributeAppender getTitleAppender(String stationCode) {
                String tooltipValue = null;
                
                if (stationCode.equals(Constants.STATION_CODE__BRIDGEND)) {
                    tooltipValue = "Bridgend";
                } else if (stationCode.equals(Constants.STATION_CODE__CARDIFF_CENTRAL)) {
                    tooltipValue = "Cardiff Central";
                } else if (stationCode.equals(Constants.STATION_CODE__CARDIFF_QUEEN_STREET)) {
                    tooltipValue = "Cardiff Queen Street";
                } else if (stationCode.equals(Constants.STATION_CODE__LLANTWIT_MAJOR)) {
                    tooltipValue = "Llantwit Major";
                }
                
                return new AttributeAppender("title", tooltipValue);
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
