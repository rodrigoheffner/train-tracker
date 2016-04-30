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

/**
 *
 * @author rodrigo
 */
public class TrainTime {
    private String standardDepartureTime = "";
    private String estimatedDepartureTime = "";
    private String response = "";

    public TrainTime(String standardDepartureTime, String estimatedDepartureTime, String response) {
        this.standardDepartureTime = standardDepartureTime;
        this.estimatedDepartureTime = estimatedDepartureTime;
        this.response = response;
    }

    public String getStandardDepartureTime() {
        return standardDepartureTime;
    }

    public void setStandardDepartureTime(String standardDepartureTime) {
        this.standardDepartureTime = standardDepartureTime;
    }

    public String getEstimatedDepartureTime() {
        return estimatedDepartureTime;
    }

    public void setEstimatedDepartureTime(String estimatedDepartureTime) {
        this.estimatedDepartureTime = estimatedDepartureTime;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
