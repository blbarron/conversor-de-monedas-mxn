package api;

import java.util.Map;

public class ExchangeRateResponse {
    // Mapa para almacenar las tasas de cambio por código de moneda
    private Map<String, Double> conversion_rates;

    // Método getter para acceder a las tasas de cambio
    public Map<String, Double> getConversionRates() {
        return conversion_rates;
    }
}