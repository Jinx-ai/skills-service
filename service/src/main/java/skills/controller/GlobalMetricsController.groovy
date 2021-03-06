/**
 * Copyright 2020 SkillTree
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package skills.controller

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import skills.metrics.ChartParams
import skills.metrics.MetricsService
import skills.metrics.model.MetricsChart
import skills.metrics.model.Section
import skills.profile.EnableCallStackProf
import skills.services.AdminUsersService

@RestController
@RequestMapping("/metrics")
@Slf4j
@EnableCallStackProf
class GlobalMetricsController {

    @Autowired
    AdminUsersService adminUsersService

    @Autowired
    MetricsService metricsService

    @RequestMapping(value = "/{section}", method =  RequestMethod.GET, produces = "application/json")
    List<MetricsChart> getAllGlobalSectionMetricsCharts(@PathVariable("section") Section section){
        return metricsService.loadChartsForSection(section, null, [:])
    }

    @RequestMapping(value = "/{section}/{sectionId}/metric/{chartBuilderId}", method =  RequestMethod.GET, produces = "application/json")
    MetricsChart getGlobalSectionMetricsChart(@PathVariable("section") Section section,
                                              @PathVariable(ChartParams.SECTION_ID) String sectionId,
                                              @PathVariable(ChartParams.CHART_BUILDER_ID) String chartBuilderId,
                                              @RequestParam Map<String,String> chartProps){

        chartProps.put(ChartParams.SECTION_ID, sectionId)
        return metricsService.loadChartForSection(chartBuilderId, section, null, chartProps)
    }
}
