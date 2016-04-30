/*
 * Copyright 2016 rodrigo.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.rodrigobezerra.model;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.rodrigobezerra.Service;

/**
 *
 * @author rodrigo
 */
public class TrainRoute {
    private String from;
    private String to;
    private TrainTime trainTime;

    public TrainRoute(String from, String to) {
        this.from = from;
        this.to = to;
        
        try {
            // TODO: Move this code somewhere else
            trainTime = Service.getNextDeparture(from, to);
        } catch (Exception ex) {
            // TODO: Add logging
            System.out.println(ex);
        }
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public TrainTime getTrainTime() {
        return trainTime;
    }

    public void setTrainTime(TrainTime trainTime) {
        this.trainTime = trainTime;
    }
}
