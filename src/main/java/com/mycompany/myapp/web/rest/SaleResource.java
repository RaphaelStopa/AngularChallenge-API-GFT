package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.SaleRepository;
import com.mycompany.myapp.service.SaleService;
import com.mycompany.myapp.service.dto.SaleDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api")
public class SaleResource {

    private final Logger log = LoggerFactory.getLogger(SaleResource.class);

    private static final String ENTITY_NAME = "sale";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SaleService saleService;

    private final SaleRepository saleRepository;

    public SaleResource(SaleService saleService, SaleRepository saleRepository) {
        this.saleService = saleService;
        this.saleRepository = saleRepository;
    }

    @PostMapping("/sales")
    public ResponseEntity<SaleDTO> createSale(@RequestBody SaleDTO saleDTO) throws URISyntaxException {
        log.debug("REST request to save Sale : {}", saleDTO);
        if (saleDTO.getId() != null) {
            throw new BadRequestAlertException("A new sale cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SaleDTO result = saleService.save(saleDTO);
        return ResponseEntity
            .created(new URI("/api/sales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/sales/{id}")
    public ResponseEntity<SaleDTO> updateSale(@PathVariable(value = "id", required = false) final Long id, @RequestBody SaleDTO saleDTO)
        throws URISyntaxException {
        log.debug("REST request to update Sale : {}, {}", id, saleDTO);
        if (saleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, saleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!saleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SaleDTO result = saleService.save(saleDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, saleDTO.getId().toString()))
            .body(result);
    }

    @PatchMapping(value = "/sales/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SaleDTO> partialUpdateSale(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SaleDTO saleDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Sale partially : {}, {}", id, saleDTO);
        if (saleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, saleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!saleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SaleDTO> result = saleService.partialUpdate(saleDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, saleDTO.getId().toString())
        );
    }

    @GetMapping("/sales-cep/{code}")
    public ResponseEntity<Boolean> getValidateZipCode(@PathVariable String code) {
        return ResponseEntity.ok().body(verifiZipcode(code));
    }

    @GetMapping("/sales")
    public ResponseEntity<List<SaleDTO>> getAllSales() {
        log.debug("REST request to get a page of Sales");
        List<SaleDTO> page = saleService.findAllByUserId();
        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/sales-total")
    public ResponseEntity<Double> getAllTotal() {
        log.debug("REST request to get a page of Sales");
        Double total = saleService.getTotal();
        return ResponseEntity.ok().body(total);
    }

    @GetMapping("/sales/{id}")
    public ResponseEntity<SaleDTO> getSale(@PathVariable Long id) {
        log.debug("REST request to get Sale : {}", id);
        Optional<SaleDTO> saleDTO = saleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(saleDTO);
    }

    @DeleteMapping("/sales/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable Long id) {
        log.debug("REST request to delete Sale : {}", id);
        saleService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    public static final String getHttpGET(String urlToRead) {
        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(urlToRead);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException ex) {}

        return result.toString();
    }

    public static boolean verifiZipcode(String zipcode) {
        String newZipcode = zipcode.replaceAll("[^0-9]", "");

        if (newZipcode.length() != 8) {
            return false;
        } else {
            String url = "https://api.invertexto.com/v1/cep/" + newZipcode + "?token=363|hBS8Be7ymI5zOkFDWw5pDkcthZtjxhqw";

            JSONObject obj = new JSONObject(getHttpGET(url));

            if (obj.has("cep")) {
                return true;
            } else {
                return false;
            }
        }
    }
}
