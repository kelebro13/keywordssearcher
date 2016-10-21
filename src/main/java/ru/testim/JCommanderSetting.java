package ru.testim;


import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.converters.PathConverter;
import com.beust.jcommander.converters.URLConverter;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class JCommanderSetting {

    @Parameter(description = "Paths files with keywords", validateWith = PathValidator.class, converter = PathConverter.class)
    private List<Path> path;

    @Parameter(names = {"-u", "--proxyurl"}, description = "Proxy server url", validateWith = ProxyValidator.class, converter = URLConverter.class, required = false)
    private URL urlProxy;

    @Parameter(names = {"-p", "--proxyport"}, description = "Proxy server port", validateWith = ProxyValidator.class, required = false)
    private Integer portProxy;

    @Parameter(names = "--help", help = true)
    private boolean help = false;


    public List<Path> getPath() {
        return path;
    }

    public void setPath(List<Path> path) {
        this.path = path;
    }

    public Integer getPortProxy() {
        return portProxy;
    }

    public void setPortProxy(Integer portProxy) {
        this.portProxy = portProxy;
    }

    public URL getUrlProxy() {
        return urlProxy;
    }

    public void setUrlProxy(URL urlProxy) {
        this.urlProxy = urlProxy;
    }

    public boolean isHelp() {
        return help;
    }

    public boolean isHaveUrlProxy() {
        return urlProxy == null ? false : true;
    }

    public boolean isHavePortProxy(){
        return portProxy == null ? false : true;
    }

    public void setHelp(boolean help) {
        this.help = help;
    }

    public static class PathValidator implements IParameterValidator {
        @Override
        public void validate(String name, String value) throws ParameterException {
            if (!Files.exists(Paths.get(value))) {
                throw new ParameterException("Данного файла не существует: " + value);
            }
        }
    }

    public static class ProxyValidator implements IParameterValidator {

        @Override
        public void validate(String name, String value) throws ParameterException {

            if ("-u".equals(name)) {
                try {
                    URL url = new URL(value);
                } catch (MalformedURLException e) {
                    throw new ParameterException(e.getLocalizedMessage());
                }
            } else if ("-p".equals(name)) {
                Integer port = null;
                try {
                    port = Integer.valueOf(value);
                } catch (NumberFormatException e) {
                    throw new ParameterException("параметр --proxyport не является числом: " + value);
                }
                if (port < 0 || port > 65535) {
                    throw new ParameterException("параметр --proxyport не входит в диапазон 0 - 65535: " + value);
                }
            }
        }
    }
}
