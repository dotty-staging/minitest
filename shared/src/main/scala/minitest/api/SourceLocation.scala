/*
 * Copyright (c) 2014-2019 by The Minitest Project Developers.
 * Some rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package minitest.api

import scala.quoted.{_, given}

final case class SourceLocation(
  fileName: Option[String],
  filePath: Option[String],
  line: Int
)

object SourceLocation {
  inline implicit def fromContext: SourceLocation = ${ fromContextImpl }

  def fromContextImpl(implicit qctx: QuoteContext): Expr[SourceLocation] = {
    import qctx.tasty._
    val pos = rootPosition
    val fileName = pos.sourceFile.jpath.getFileName.toString
    val path = pos.sourceFile.jpath.getParent.toString
    val line = pos.startLine + 1
    '{SourceLocation(Some(${fileName.toExpr}), Some(${path.toExpr}), ${line.toExpr})}
  }
}
