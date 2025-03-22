package com.knuxstuff.rescueradar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.rememberMapState
import com.mapbox.maps.extension.compose.style.BooleanValue
import com.mapbox.maps.extension.compose.style.ColorValue
import com.mapbox.maps.extension.compose.style.DoubleValue
import com.mapbox.maps.extension.compose.style.LongValue
import com.mapbox.maps.extension.compose.style.StringValue
import com.mapbox.maps.extension.compose.style.layers.generated.CircleLayer
import com.mapbox.maps.extension.compose.style.layers.generated.HeatmapLayer
import com.mapbox.maps.extension.compose.style.sources.GeoJSONData
import com.mapbox.maps.extension.compose.style.sources.generated.rememberGeoJsonSourceState
import com.mapbox.maps.extension.style.expressions.dsl.generated.get
import com.mapbox.maps.extension.style.expressions.dsl.generated.heatmapDensity
import com.mapbox.maps.extension.style.expressions.dsl.generated.literal
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.interpolate
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.linear
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Composable
fun MapScreen() {
    var rememberLayerPicker by remember {
        mutableStateOf(false)
    };
    val coroutineScope = rememberCoroutineScope()
    val mapState = rememberMapState {
        coroutineScope.launch {
            mapLoadingErrorEvents.onEach {
                // Error occurred when loading the map, try to handle it gracefully here
            }
        }
        coroutineScope.launch {
            styleLoadedEvents.first().let {
                // Map is setup and style has loaded, Now you can add data or make other map adjustments.
            }
        }
    }
    MapboxMap(
        modifier = Modifier.fillMaxSize(),
        mapState = mapState
    ) {
        if(rememberLayerPicker) {
            heatmap()
            circle()
        }
        else {
            circle()
        }

        }
    FloatingActionButton(
        onClick = { rememberLayerPicker = !rememberLayerPicker },
    ) {
        Icon(Icons.Filled.Home, "Floating action button.")
    }
}
@Composable
fun circle() {
        CircleLayer(
            sourceState = rememberGeoJsonSourceState {
                data = GeoJSONData(        "https://192.168.68.70:4443/map.geojson"
                )
                cluster = BooleanValue(false)
            }
        ) {
            circleRadius = DoubleValue(5.0)
            circleColor = ColorValue(Color.Red)
            circleOpacity = DoubleValue(0.8)
            circleStrokeColor = ColorValue(Color.White)
        }
}
@Composable
fun heatmap() {
    HeatmapLayer(
        sourceState = rememberGeoJsonSourceState {
            data = GeoJSONData(        "https://localhost:4443/map.geojson"
            )
            cluster = BooleanValue(false)

        }
    ) {
        minZoom = LongValue(6)
        sourceLayer = StringValue("earthquakes")
// Begin color ramp at 0-stop with a 0-transparancy color
// to create a blur-like effect.
        heatmapColor = ColorValue(
            interpolate {
                linear()
                heatmapWeight
                stop {
                    literal(0)
                    rgba(33.0, 102.0, 172.0, 0.0)
                }
                stop {
                    literal(0.1)
                    rgb(0.0, 255.0, 0.0)
                }
                stop {
                    literal(0.5)
                    rgb(255.0, 255.0, 0.0)
                }
                stop {
                    literal(1)
                    rgb(178.0, 0.0, 0.0)
                }})
        heatmapWeight = DoubleValue(
            interpolate {
                linear()
                get { literal("mag") }
                stop {
                    literal(0)
                    literal(0)
                }
                stop {
                    literal(6)
                    literal(1)
                }
            }
        )
// Increase the heatmap color weight weight by zoom level
// heatmap-intensity is a multiplier on top of heatmap-weight
        heatmapIntensity = DoubleValue(
            interpolate {
                linear()
                zoom()
                stop {
                    literal(0)
                    literal(1)
                }
                stop {
                    literal(9)
                    literal(3)
                }
            }
        )
// Adjust the heatmap radius by zoom level
        heatmapRadius = DoubleValue(
            interpolate {
                linear()
                zoom()
                stop {
                    literal(0)
                    literal(2)
                }
                stop {
                    literal(9)
                    literal(20)
                }
            }
        )
// Transition from heatmap to circle layer by zoom level
    }
}
