/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.modules;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * The special module classloader for the system module.
 *
 * @author <a href="mailto:david.lloyd@redhat.com">David M. Lloyd</a>
 */
final class SystemModuleClassLoader extends ModuleClassLoader {

    SystemModuleClassLoader(final Module module, final Set<Module.Flag> flags, final AssertionSetting setting) {
        super(module, flags, setting, null);
    }

    Set<String> getExportedPaths() {
        final Package[] packages = getPackages();
        final HashSet<String> set = new HashSet<String>(packages.length);
        for (Package pkg : packages) {
            set.add(pkg.getName().replace('.', '/'));
        }
        return set;
    }

    protected Class<?> findClass(final String className, final boolean exportsOnly) throws ClassNotFoundException {
        return findSystemClass(className);
    }

    protected String findLibrary(final String libname) {
        return null;
    }

    public URL findResource(final String name, final boolean exportsOnly) {
        return getSystemResource(name);
    }

    public Enumeration<URL> findResources(final String name, final boolean exportsOnly) throws IOException {
        return getSystemResources(name);
    }

    public InputStream findResourceAsStream(final String name, final boolean exportsOnly) {
        return getSystemResourceAsStream(name);
    }

    Resource getRawResource(final String root, final String name) {
        if ("".equals(root)) {
            return new URLResource(getResource(name));
        } else {
            return null;
        }
    }
}