/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.rust.debugger.settings

import com.intellij.openapi.options.ConfigurableUi
import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.components.Label
import com.intellij.ui.layout.Cell
import com.intellij.ui.layout.CellBuilder
import com.intellij.ui.layout.Row
import com.intellij.ui.layout.panel
import com.intellij.util.ui.UIUtil.ComponentStyle.SMALL
import org.rust.debugger.GDBRenderers
import org.rust.debugger.LLDBRenderers
import javax.swing.JComponent
import javax.swing.JLabel

class RsDebuggerDataViewConfigurableUi : ConfigurableUi<RsDebuggerSettings> {

    private val lldbRenderers = ComboBox<LLDBRenderers>().apply {
        LLDBRenderers.values().forEach { addItem(it) }
    }

    private val gdbRenderers = ComboBox<GDBRenderers>().apply {
        GDBRenderers.values().forEach { addItem(it) }
    }

    override fun isModified(settings: RsDebuggerSettings): Boolean =
        lldbRenderers.selectedIndex != settings.lldbRenderers.ordinal || gdbRenderers.selectedIndex != settings.gdbRenderers.ordinal

    override fun apply(settings: RsDebuggerSettings) {
        settings.lldbRenderers = LLDBRenderers.fromIndex(lldbRenderers.selectedIndex)
        settings.gdbRenderers = GDBRenderers.fromIndex(gdbRenderers.selectedIndex)
    }

    override fun reset(settings: RsDebuggerSettings) {
        lldbRenderers.selectedIndex = settings.lldbRenderers.ordinal
        gdbRenderers.selectedIndex = settings.gdbRenderers.ordinal
    }

    override fun getComponent(): JComponent = panel {
        row("LLDB renderers:") { lldbRenderers() }
        row("GDB renderers:") { gdbRenderers() }
        row { smallLabelWithGap("Changing these options will affect next debug session") }
    }
}
