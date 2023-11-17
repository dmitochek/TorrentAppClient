import express from 'express';
import WebTorrent from 'webtorrent';
import fs from 'fs';
import os from 'os';
import parseArgs from 'minimist';
const PORT = 3000;

const { path } = parseArgs(process.argv.slice(2));

let client = new WebTorrent();
let app = express();

app.set('view engine', 'ejs');

app.listen(PORT, function (err)
{
    if (err) console.log(err);
    console.log("Server listening on PORT", PORT);
});

let error_message = "";


app.get('/', function (req, res, next)
{
    res.render("index");

});

client.on('error', function (err)
{

    error_message = err.message;

});

app.get('/errors', function (req, res, next)
{

    res.status(200);
    res.json(error_message);

});

app.get('/add/:magnet', function (req, res)
{

    let magnet = atob(req.params.magnet);
    console.log("here" + magnet);

    client.add(magnet, { path: path }, function (torrent)
    {

        let files = [];

        torrent.files.forEach(function (data)
        {

            files.push({
                name: data.name,
                length: data.length
            });

        });
        console.log(files);
        res.status(200)
        res.json(files);

    });

});

app.get('/stream/:magnet/:file_id', async (req, res, next) =>
{
    let file = {};

    let magnet = atob(req.params.magnet);

    let chosenFileID = req.params.file_id;

    let tor = await client.get(magnet);

    file = tor.files[chosenFileID];

    let range = req.headers.range;

    if (!range)
    {
        let err = new Error("Wrong range");
        err.status = 416;

        return next(err);
    }

    let positions = range.replace(/bytes=/, "").split("-");


    const start = parseInt(positions[0], 10);

    let file_size = file.length;

    let end = positions[1] ? parseInt(positions[1], 10) : file_size - 1;

    let chunksize = (end - start) + 1;

    let head = {
        "Content-Range": "bytes " + start + "-" + end + "/" + file_size,
        "Accept-Ranges": "bytes",
        "Content-Length": chunksize,
        "Content-Type": "video/mp4"
    }

    res.writeHead(206, head);

    let stream_position = {
        start: start,
        end: end
    }

    let stream = file.createReadStream(stream_position);

    stream.pipe(res);

    stream.on("error", function (err)
    {

        return next(err);

    });

});

