package ru.nshi.api;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ru.nshi.api.schemas.BadAlgorithm;
import ru.nshi.api.schemas.BadContentTypeError;
import ru.nshi.api.schemas.BadJson;
import ru.nshi.api.schemas.NullArrayError;
import ru.nshi.api.schemas.SortingRequest;
import ru.nshi.api.schemas.SortingResponse;
import ru.nshi.jsonSorter.AlgorithmNotSupportedException;
import ru.nshi.jsonSorter.JsonSorter;
import ru.nshi.sorterWrapper.SorterWrapper;
import ru.nshi.sorters.BubbleSorter;
import ru.nshi.sorters.MergeSorter;
import ru.nshi.sorters.SelectionSorter;


/*
 *
 *  Создать обработчик: POST запрос на путь `/sorting`
 *  и заголовок `Content-Type: application/json`
 *
 *  Данный обработчик должен отсортировать посутпившие данные. Данные
 *  поступают в виде JSON объекта. Пример JSON объекта:
 *
 *  {
 *      "values": [
 *          7,
 *          2,
 *          3,
 *          4,
 *          1
 *      ]
 *  }
 *
 *  В параметрах запроса (query params) может находится параметр `algorithm`,
 *  указывающий на тип сортировки.
 *
 *  - Если параметра `algorithm` нет, то выполняется сортировка по
 *    умолчанию (bubble)
 *
 *  - Если сортировка не существует, то ответить на запрос кодом 404
 *
 *  - Если JSON объект не валидный, то ответить на запрос кодом 400
 *
 *  - При успешном выполнении ответить кодом 200 и вернуть
 *    следующий JSON объект:
 *
 *  {
 *      "time": 100,
 *      "values": [
 *          1, 2, 3, 4, 7
 *      ]
 *  }
 *
 *  Если произошла ошибка, то вернуть следующий `JSON`:
 *  {
 *      "errorMessage": < Message, containing the description of the error >
 *  }
 *
 */

@WebServlet("/sorting")
public class SorterServlet extends HttpServlet {

    /*
     *  Making `application/json` a variable saves us from accident typos.
     */
    private static final String APPLICATION_JSON = "application/json";

    private ObjectMapper mapper;
    private JsonSorter jwrapper;

    @Override
    public void init() throws ServletException {
        this.mapper     = new ObjectMapper();

        HashMap<String, SorterWrapper> sorters = new HashMap<>();
        sorters.put("bubble",       new SorterWrapper(new BubbleSorter()));
        sorters.put("selection",    new SorterWrapper(new SelectionSorter()));
        sorters.put("merge",        new SorterWrapper(new MergeSorter()));

        this.jwrapper   = new JsonSorter(
            sorters
        );
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Make sure that Content-Type is not null
        if (req.getContentType() == null) {
            resp.setStatus(400);

            this.mapper.writeValue(
                resp.getWriter(),
                new BadContentTypeError(APPLICATION_JSON, "null")
            );

            return;
        }

        // The request should be of content type `application/json`
        if (!req.getContentType().contains(APPLICATION_JSON)) {
            // Content-Type is not supported
            resp.setStatus(400);
            this.mapper.writeValue(
                resp.getWriter(),
                new BadContentTypeError(APPLICATION_JSON, req.getContentType())
            );

            return;
        }

        // Interpret the request body as a SortingRequest object if possible
        SortingRequest sortReq;

        // Need to read the input string here since we can't call InputStream multiple times
        String inputString = req.getReader().readLine();
        try {
            sortReq = this.mapper.readValue(inputString, SortingRequest.class);
        } catch (DatabindException e) {
            // Failed to databind
            resp.setStatus(400);
            this.mapper.writeValue(
                resp.getWriter(),
                new BadJson(inputString, SortingRequest.class.getSimpleName())
            );

            return;
        }

        // Check if the array passed is null
        if (sortReq.getValues() == null) {
            // Should respond with error on null array
            resp.setStatus(400);
            this.mapper.writeValue(
                resp.getWriter(),
                new NullArrayError()
            );
        }

        // Everything is fine. Sort now

        // Find out what is the algorithm via query params
        String algorithm = req.getParameter("algorithm");
        if (algorithm == null) {
            algorithm = "bubble";
        }

        int[] sorted;

        // Find out the time of sorting
        long timeBeforeSort = System.currentTimeMillis();
        try {
            sorted = this.jwrapper.sort(sortReq.getValues(), algorithm);
        } catch (AlgorithmNotSupportedException e) {
            // Unsopported algorithm
            resp.setStatus(404);
            this.mapper.writeValue(
                resp.getWriter(),
                new BadAlgorithm(algorithm)
            );

            return;
        } catch (Exception e) {
            // Oh, that's bad
            resp.setStatus(500);
            resp.getWriter().println("jwrapper.sort() failed");
            return;
        }
        long timeAfterSort = System.currentTimeMillis();
        int time = (int) (timeAfterSort - timeBeforeSort);

        SortingResponse sresp = new SortingResponse(sorted, time);

        resp.setContentType(APPLICATION_JSON);
        mapper.writeValue(resp.getOutputStream(), sresp);
    }
}
